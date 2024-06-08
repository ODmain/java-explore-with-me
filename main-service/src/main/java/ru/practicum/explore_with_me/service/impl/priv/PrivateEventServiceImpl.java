package ru.practicum.explore_with_me.service.impl.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.dto.event.*;
import ru.practicum.explore_with_me.dto.request.RequestOutputDto;
import ru.practicum.explore_with_me.exception.ValidException;
import ru.practicum.explore_with_me.mapper.CategoryMapper;
import ru.practicum.explore_with_me.mapper.EventMapper;
import ru.practicum.explore_with_me.mapper.LocationMapper;
import ru.practicum.explore_with_me.mapper.UserMapper;
import ru.practicum.explore_with_me.model.Category;
import ru.practicum.explore_with_me.model.Event;
import ru.practicum.explore_with_me.model.Location;
import ru.practicum.explore_with_me.model.User;
import ru.practicum.explore_with_me.service.api.LocationService;
import ru.practicum.explore_with_me.service.api.priv.PrivateEventService;
import ru.practicum.explore_with_me.storage.CategoryStorage;
import ru.practicum.explore_with_me.storage.EventStorage;
import ru.practicum.explore_with_me.storage.UserStorage;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrivateEventServiceImpl implements PrivateEventService {
    private final CategoryStorage categoryStorage;
    private final CategoryMapper categoryMapper;
    private final LocationService locationService;
    private final LocationMapper locationMapper;
    private final EventMapper eventMapper;
    private final EventStorage eventStorage;
    private final UserMapper userMapper;
    private final UserStorage userStorage;


    @Override
    @Transactional
    public EventOutputDto addEvent(Long userId, NewEventDto newEventDto) {
        User user = validUser(userId);
        Category category = categoryStorage.findById(newEventDto.getCategory()).orElseThrow(() ->
                new ValidException("Category was not found", HttpStatus.NOT_FOUND));
        validDate(newEventDto.getEventDate());
        Location location = locationService.addLocation(newEventDto.getLocation());
        return eventMapper.toEventOutputDto(eventStorage.save(eventMapper
                        .toEventFromNew(newEventDto, category, location, user)), categoryMapper.toCategoryOutputDto(category),
                userMapper.toUserOutputDto(user), locationMapper.toLocationDto(location));
    }

    @Override
    public List<EventByIdOutputDto> getEventsByUserId(Long userId, Integer from, Integer size) {
        validUser(userId);
        Pageable pageable = PageRequest.of(from / size, size, Sort.unsorted().descending());
        List<Event> events = eventStorage.findAllByInitiatorId(userId, pageable).getContent();
        return null;

    }

    @Override
    public EventOutputDto getEventById(Long userId, Long eventId) {
        return null;
    }

    @Override
    public RequestOutputDto getOwnEventRequests(Long userId, Long eventId) {
        return null;
    }

    @Override
    @Transactional
    public EventOutputDto updateEvent(Long userId, Long eventId, UpdateEventDto updateEventDto) {
        return null;
    }

    @Override
    @Transactional
    public EventUpdateRequestOutputDto updateEventRequestStatus(Long userId, Long eventId,
                                                                EventUpdateRequestInputDto eventUpdateRequestInputDto) {
        return null;
    }

    private User validUser(Long userId) {
        return userStorage.findById(userId).orElseThrow(() ->
                new ValidException("User was not found", HttpStatus.NOT_FOUND));
    }

    private void validDate(LocalDateTime localDateTime) {
        if (localDateTime.isBefore(LocalDateTime.now())) {
            throw new ValidException("Date can not be before now", HttpStatus.CONFLICT);
        }
    }
}
