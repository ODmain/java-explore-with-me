package ru.practicum.service.api.priv;

import ru.practicum.dto.event.*;
import ru.practicum.dto.request.RequestOutputDto;

import java.util.List;

public interface PrivateEventService {

    EventOutputDto addEvent(Long userId, NewEventDto updateEventDto);

    List<EventByIdOutputDto> getEventsByUserId(Long userId, Integer from, Integer size);

    EventOutputDto getEventById(Long userId, Long eventId);

    List<RequestOutputDto> getOwnEventRequests(Long userId, Long eventId);

    EventOutputDto updateEvent(Long userId, Long eventId, UpdateEventDto updateEventDto);

    EventUpdateRequestOutputDto updateEventRequestStatus(Long userId, Long eventId,
                                                         EventUpdateRequestInputDto eventUpdateRequestInputDto);
}
