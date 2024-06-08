package ru.practicum.explore_with_me.service.api.pub;

import ru.practicum.explore_with_me.dto.event.EventByIdOutputDto;
import ru.practicum.explore_with_me.dto.event.EventOutputDto;

import java.util.List;

public interface PublicEventService {
    List<EventOutputDto> getAllEvents(String text, List<Long> categories, Boolean paid, String rangeStart,
                                      String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size);

    EventByIdOutputDto getEventById(Long id);
}
