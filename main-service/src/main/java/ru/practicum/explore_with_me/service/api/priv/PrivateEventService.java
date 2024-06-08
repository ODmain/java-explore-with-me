package ru.practicum.explore_with_me.service.api.priv;

import ru.practicum.explore_with_me.dto.event.*;
import ru.practicum.explore_with_me.dto.request.RequestOutputDto;

import java.util.List;

public interface PrivateEventService {

    EventOutputDto addEvent(Long userId, NewEventDto updateEventDto);

    List<EventByIdOutputDto> getEventsByUserId(Long userId, Integer from, Integer size);

    EventOutputDto getEventById(Long userId, Long eventId);

    RequestOutputDto getOwnEventRequests(Long userId, Long eventId);

    EventOutputDto updateEvent(Long userId, Long eventId, UpdateEventDto updateEventDto);

    EventUpdateRequestOutputDto updateEventRequestStatus(Long userId, Long eventId,
                                                         EventUpdateRequestInputDto eventUpdateRequestInputDto);
}
