package ru.practicum.explore_with_me.controller.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import ru.practicum.explore_with_me.dto.event.*;
import ru.practicum.explore_with_me.dto.request.RequestOutputDto;
import ru.practicum.explore_with_me.service.api.priv.PrivateEventService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {
    private final PrivateEventService privateEventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventOutputDto addEvent(@PathVariable Long userId,
                                   @RequestBody @Valid NewEventDto newEventDto) {
        return privateEventService.addEvent(userId, newEventDto);
    }

    @GetMapping
    public List<EventByIdOutputDto> getEventsByUserId(@PathVariable Long userId,
                                                      @RequestParam(defaultValue = "0") Integer from,
                                                      @RequestParam(defaultValue = "10") Integer size) {
        return privateEventService.getEventsByUserId(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventOutputDto getEventById(@PathVariable Long userId,
                                       @PathVariable Long eventId) {
        return privateEventService.getEventById(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestOutputDto> getOwnEventRequests(@PathVariable Long userId,
                                                @PathVariable Long eventId) {
        return privateEventService.getOwnEventRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventOutputDto updateEvent(@PathVariable Long userId,
                                      @PathVariable Long eventId,
                                      @RequestBody @Valid UpdateEventDto updateEventDto) {
        return privateEventService.updateEvent(userId, eventId, updateEventDto);
    }

    @PatchMapping("/{eventId}/requests")
    public EventUpdateRequestOutputDto updateEventRequestStatus(@PathVariable Long userId,
                                                                @PathVariable Long eventId,
                                                                @RequestBody @Valid EventUpdateRequestInputDto eventUpdateRequestInputDto) {
        return privateEventService.updateEventRequestStatus(userId, eventId, eventUpdateRequestInputDto);
    }
}
