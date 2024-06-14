package ru.practicum.controller.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.EventByIdOutputDto;
import ru.practicum.dto.event.EventOutputDto;
import ru.practicum.service.api.pub.PublicEventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {
    private final PublicEventService eventService;

    @GetMapping
    public List<EventByIdOutputDto> getAllEvents(@RequestParam(required = false) String text,
                                                 @RequestParam(required = false) List<Long> categories,
                                                 @RequestParam(required = false) Boolean paid,
                                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                                 @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
                                                 @RequestParam(required = false) String sort,
                                                 @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer from,
                                                 @RequestParam(required = false, defaultValue = "10") @Positive Integer size,
                                                 HttpServletRequest servletRequest) {
        return eventService.getAllEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, servletRequest);
    }

    @GetMapping("/{eventId}")
    public EventOutputDto getEventById(@PathVariable Long eventId,
                                       HttpServletRequest servletRequest) {
        return eventService.getEventById(eventId, servletRequest);
    }
}
