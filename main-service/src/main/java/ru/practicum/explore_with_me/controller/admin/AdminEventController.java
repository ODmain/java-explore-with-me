package ru.practicum.explore_with_me.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.constant.State;
import ru.practicum.explore_with_me.dto.event.EventOutputDto;
import ru.practicum.explore_with_me.dto.event.UpdateEventDto;
import ru.practicum.explore_with_me.service.api.admin.AdminEventService;

import javax.validation.Valid;
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
                                          @RequestParam(required = false, defaultValue = "0") Integer from,
                                          @RequestParam(required = false, defaultValue = "10") Integer size) {
        return adminEventService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

}
