package ru.practicum.explore_with_me.service.impl.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.constant.State;
import ru.practicum.explore_with_me.constant.Status;
import ru.practicum.explore_with_me.dto.event.*;
import ru.practicum.explore_with_me.dto.request.RequestOutputDto;
import ru.practicum.explore_with_me.exception.ValidException;
import ru.practicum.explore_with_me.mapper.EventMapper;
import ru.practicum.explore_with_me.mapper.RequestMapper;
import ru.practicum.explore_with_me.model.*;
import ru.practicum.explore_with_me.service.api.LocationService;
import ru.practicum.explore_with_me.service.api.priv.PrivateEventService;
import ru.practicum.explore_with_me.storage.CategoryStorage;
import ru.practicum.explore_with_me.storage.EventStorage;
import ru.practicum.explore_with_me.storage.RequestStorage;
import ru.practicum.explore_with_me.storage.UserStorage;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrivateEventServiceImpl implements PrivateEventService {
    private final CategoryStorage categoryStorage;
    private final LocationService locationService;
    private final EventMapper eventMapper;
    private final EventStorage eventStorage;
    private final UserStorage userStorage;
    private final RequestStorage requestStorage;
    private final RequestMapper requestMapper;


    @Override
    @Transactional
    public EventOutputDto addEvent(Long userId, NewEventDto newEventDto) {
        User user = validUser(userId);
        Category category = categoryStorage.findById(newEventDto.getCategory()).orElseThrow(() ->
                new ValidException("Category was not found", HttpStatus.NOT_FOUND));
        validDate(newEventDto.getEventDate());
        Location location = locationService.addLocation(newEventDto.getLocation());
        Event event = eventMapper.toEvent(newEventDto);
        event.setViews(0L);
        event.setConfirmedRequests(0L);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(State.PENDING);
        event.setInitiator(user);
        event.setCategory(category);
        event.setLocation(location);
        return eventMapper.toEventOutputDto(eventStorage.save(event));
    }

    @Override
    public List<EventByIdOutputDto> getEventsByUserId(Long userId, Integer from, Integer size) {
        validUser(userId);
        Pageable pageable = PageRequest.of(from / size, size, Sort.unsorted().descending());
        List<Event> events = eventStorage.findAllByInitiatorId(userId, pageable).getContent();
        return eventMapper.toEventByIdOutputDtoList(events);

    }

    @Override
    public EventOutputDto getEventById(Long userId, Long eventId) {
        validUser(userId);
        Event event = validEvent(eventId);
        return eventMapper.toEventOutputDto(event);
    }

    @Override
    public List<RequestOutputDto> getOwnEventRequests(Long userId, Long eventId) {
        validUser(userId);
        Event event = validEvent(eventId);
        validOwner(userId, event.getInitiator().getId());
        List<Request> requests = requestStorage.findAllByEventId(eventId);
        return requestMapper.toRequestOutputDtoList(requests);
    }

    @Override
    @Transactional
    public EventOutputDto updateEvent(Long userId, Long eventId, UpdateEventDto updateEventDto) {
        validUser(userId);
        Event event = validEvent(eventId);
        if (event.getState().equals(State.PUBLISHED)) {
            throw new ValidException("You can't update an event that is published", HttpStatus.CONFLICT);
        }
        if (updateEventDto.getAnnotation() != null) {
            event.setAnnotation(updateEventDto.getAnnotation());
        }
        if (updateEventDto.getCategory() != null) {
            Category category = categoryStorage.findById(updateEventDto.getCategory()).orElseThrow(() ->
                    new ValidException("Category was not found", HttpStatus.NOT_FOUND));
            event.setCategory(category);
        }
        if (updateEventDto.getDescription() != null) {
            event.setDescription(updateEventDto.getDescription());
        }
        if (updateEventDto.getEventDate() != null) {
            if (updateEventDto.getEventDate().isBefore(LocalDateTime.now())) {
                throw new ValidException("Event date is before current date", HttpStatus.BAD_REQUEST);
            }
            event.setEventDate(updateEventDto.getEventDate());
        }
        if (updateEventDto.getLocation() != null) {
            event.setLocation(locationService.addLocation(updateEventDto.getLocation()));
        }
        if (updateEventDto.getPaid() != null) {
            event.setPaid(updateEventDto.getPaid());
        }
        if (updateEventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventDto.getParticipantLimit());
        }
        if (updateEventDto.getRequestModeration() != null) {
            event.setRequestModeration(updateEventDto.getRequestModeration());
        }
        if (updateEventDto.getStateAction() != null) {
            if (event.getState().equals(State.PENDING)) {
                if (updateEventDto.getStateAction().equals("PUBLISH_EVENT")) {
                    event.setState(State.PUBLISHED);
                } else if (updateEventDto.getStateAction().equals("CANCEL_REVIEW")) {
                    event.setState(State.CANCELED);
                }
            } else if (event.getState().equals(State.CANCELED)) {
                if (updateEventDto.getStateAction().equals("SEND_TO_REVIEW")) {
                    event.setState(State.PENDING);
                }
            } else {
                throw new ValidException("State action is invalid", HttpStatus.CONFLICT);
            }
        }
        if (updateEventDto.getTitle() != null) {
            event.setTitle(updateEventDto.getTitle());
        }
        event.setPublishedOn(LocalDateTime.now());
        return eventMapper.toEventOutputDto(eventStorage.save(event));
    }

    @Override
    @Transactional
    public EventUpdateRequestOutputDto updateEventRequestStatus(Long userId, Long eventId,
                                                                EventUpdateRequestInputDto eventUpdateRequestInputDto) {
        validUser(userId);
        Event event = validEvent(eventId);
        validOwner(userId, event.getInitiator().getId());
        EventUpdateRequestOutputDto eventUpdateRequestOutputDto = new EventUpdateRequestOutputDto();

        if (eventUpdateRequestInputDto.getStatus().equals(Status.CONFIRMED)) {
            if (event.getParticipantLimit() != 0) {
                Long guests = requestStorage.countAllByEventIdAndStatus(event.getId(),
                        eventUpdateRequestInputDto.getStatus()) + eventUpdateRequestInputDto.getRequestIds().size();
                if (guests > event.getParticipantLimit()) {
                    throw new ValidException("Participant limit exceeded", HttpStatus.CONFLICT);
                }
                List<Request> requests = requestStorage.findAllByIdInAndEventId(eventUpdateRequestInputDto
                        .getRequestIds(), eventId);
                for (Request request : requests) {
                    if (!request.getStatus().equals(Status.PENDING)) {
                        throw new ValidException("Request status is invalid", HttpStatus.CONFLICT);
                    }
                    request.setStatus(Status.CONFIRMED);
                }
                requestStorage.saveAll(requests);
                eventUpdateRequestOutputDto.setConfirmedRequests(requestMapper.toRequestOutputDtoList(requests));
                if (guests.equals(event.getParticipantLimit())) {
                    List<Request> requestList = requestStorage.findAllByEventIdAndStatus(eventId, Status.PENDING);
                    for (Request request : requestList) {
                        request.setStatus(Status.REJECTED);
                    }
                    requestStorage.saveAll(requestList);
                    eventUpdateRequestOutputDto.setRejectedRequests(requestMapper.toRequestOutputDtoList(requestList));
                }
                return eventUpdateRequestOutputDto;
            } else {
                List<Request> requests = requestStorage.findAllByIdInAndEventId(eventUpdateRequestInputDto
                        .getRequestIds(), eventId);
                for (Request request : requests) {
                    request.setStatus(eventUpdateRequestInputDto.getStatus());
                }
                eventUpdateRequestOutputDto.setConfirmedRequests(requestMapper.toRequestOutputDtoList(requestStorage
                        .saveAll(requests)));
                return eventUpdateRequestOutputDto;
            }
        } else if (eventUpdateRequestInputDto.getStatus().equals(Status.REJECTED)) {
            List<Request> requests = requestStorage.findAllByIdInAndEventId(eventUpdateRequestInputDto
                    .getRequestIds(), eventId);
            for (Request request : requests) {
                request.setStatus(Status.REJECTED);
            }
            eventUpdateRequestOutputDto.setRejectedRequests(requestMapper.toRequestOutputDtoList(requestStorage
                    .saveAll(requests)));
            return eventUpdateRequestOutputDto;
        } else {
            throw new ValidException("State action is invalid", HttpStatus.CONFLICT);
        }
    }

    private void validOwner(Long userId, Long id) {
        if (!id.equals(userId)) {
            throw new ValidException("You are not the owner of this event", HttpStatus.CONFLICT);
        }
    }

    private Event validEvent(Long eventId) {
        return eventStorage.findById(eventId).orElseThrow(() ->
                new ValidException("Event was not found", HttpStatus.NOT_FOUND));
    }

    private User validUser(Long userId) {
        return userStorage.findById(userId).orElseThrow(() ->
                new ValidException("User was not found", HttpStatus.NOT_FOUND));
    }

    private void validDate(LocalDateTime localDateTime) {
        if (localDateTime.isBefore(LocalDateTime.now())) {
            throw new ValidException("Date can not be before now", HttpStatus.BAD_REQUEST);
        }
    }
}
