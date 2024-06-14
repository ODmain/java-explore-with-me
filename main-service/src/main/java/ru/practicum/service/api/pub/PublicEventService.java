package ru.practicum.service.api.pub;

import ru.practicum.dto.event.EventByIdOutputDto;
import ru.practicum.dto.event.EventOutputDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface PublicEventService {
    List<EventByIdOutputDto> getAllEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                          LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from,
                                          Integer size, HttpServletRequest servletRequest);

    EventOutputDto getEventById(Long id, HttpServletRequest servletRequest);
}
