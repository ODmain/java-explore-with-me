package ru.practicum.service.api.admin;

import ru.practicum.constant.State;
import ru.practicum.dto.event.EventOutputDto;
import ru.practicum.dto.event.UpdateEventDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {
    EventOutputDto updateEvent(Long eventId, UpdateEventDto updateEventDto);

    List<EventOutputDto> getEvents(List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart,
                                   LocalDateTime rangeEnd, Integer from, Integer size);
}
