package ru.practicum.explore_with_me.service.api.admin;

import ru.practicum.explore_with_me.dto.event.EventByIdOutputDto;
import ru.practicum.explore_with_me.dto.event.EventOutputDto;
import ru.practicum.explore_with_me.dto.event.UpdateEventDto;

import java.util.List;

public interface AdminEventService {
    EventOutputDto updateEvent(Long eventId, UpdateEventDto updateEventDto);

    EventByIdOutputDto getEvents(List<Long> users, List<String> states, List<Long> categories, String rangeStart,
                                 String rangeEnd, Integer from, Integer size);
}
