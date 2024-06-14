package ru.practicum.explore_with_me.service.api.admin;

import ru.practicum.explore_with_me.constant.State;
import ru.practicum.explore_with_me.dto.event.EventOutputDto;
import ru.practicum.explore_with_me.dto.event.UpdateEventDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {
    EventOutputDto updateEvent(Long eventId, UpdateEventDto updateEventDto);

    List<EventOutputDto> getEvents(List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart,
                                   LocalDateTime rangeEnd, Integer from, Integer size);
}
