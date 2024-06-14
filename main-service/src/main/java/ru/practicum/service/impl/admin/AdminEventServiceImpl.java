package ru.practicum.service.impl.admin;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.constant.State;
import ru.practicum.dto.event.EventOutputDto;
import ru.practicum.dto.event.UpdateEventDto;
import ru.practicum.exception.ValidException;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.service.api.LocationService;
import ru.practicum.service.api.admin.AdminEventService;
import ru.practicum.storage.CategoryStorage;
import ru.practicum.storage.EventStorage;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminEventServiceImpl implements AdminEventService {
    private final EventStorage eventStorage;
    private final CategoryStorage categoryStorage;
    private final LocationService locationService;
    private final EventMapper eventMapper;

    @Override
    @Transactional
    public EventOutputDto updateEvent(Long eventId, UpdateEventDto updateEventDto) {
        Event event = eventStorage.findById(eventId).orElseThrow(() ->
                new ValidException("Event was not found", HttpStatus.NOT_FOUND));
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
                } else if (updateEventDto.getStateAction().equals("REJECT_EVENT")) {
                    event.setState(State.CANCELED);
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
    public List<EventOutputDto> getEvents(List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart,
                                          LocalDateTime rangeEnd, Integer from, Integer size) {
        if (rangeStart != null && rangeEnd != null) {
            if (rangeEnd.isBefore(rangeStart)) {
                throw new ValidException("Start date is before end date", HttpStatus.BAD_REQUEST);
            }
        }

        Pageable pageable = PageRequest.of(from / size, size);
        List<Event> events = eventStorage.findEventsByOptions(users, states, categories, rangeStart, rangeEnd, pageable)
                .getContent();
        return eventMapper.toEventOutputDtoList(events);
    }
}
