package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.constant.State;
import ru.practicum.dto.event.EventOutputDto;
import ru.practicum.dto.event.UpdateEventDto;
import ru.practicum.service.api.admin.AdminEventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/events")
public class AdminEventController {
    private final AdminEventService adminEventService;

    @PatchMapping("/{eventId}")
    public EventOutputDto updateEvent(@PathVariable Long eventId,
                                      @RequestBody @Valid UpdateEventDto updateEventDto) {
        return adminEventService.updateEvent(eventId, updateEventDto);
    }

    @GetMapping
    public List<EventOutputDto> getEvents(@RequestParam(required = false) List<Long> users,
                                          @RequestParam(required = false) List<State> states,
                                          @RequestParam(required = false) List<Long> categories,
                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                          @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                          @RequestParam(defaultValue = "10") @Positive Integer size) {
        return adminEventService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }
}
