package ru.practicum.explore_with_me.controller.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explore_with_me.dto.event.EventByIdOutputDto;
import ru.practicum.explore_with_me.dto.event.EventOutputDto;
import ru.practicum.explore_with_me.service.api.pub.PublicEventService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {
    private final PublicEventService eventService;

    @GetMapping
    public List<EventOutputDto> getAllEvents(@RequestParam(required = false) String text,
                                             @RequestParam(required = false) List<Long> categories,
                                             @RequestParam(required = false) Boolean paid,
                                             @RequestParam(required = false) String rangeStart,
                                             @RequestParam(required = false) String rangeEnd,
                                             @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                             @RequestParam(required = false) String sort,
                                             @RequestParam(defaultValue = "0") Integer from,
                                             @RequestParam(defaultValue = "10") Integer size) {
        return eventService.getAllEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping
    public EventByIdOutputDto getEventById(@RequestParam Long id) {
        return eventService.getEventById(id);
    }
}
