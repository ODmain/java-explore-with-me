package ru.practicum.service.impl.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.event.EventByIdOutputDto;
import ru.practicum.dto.event.EventOutputDto;
import ru.practicum.exception.ValidException;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Event;
import ru.practicum.service.api.pub.PublicEventService;
import ru.practicum.storage.EventStorage;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicEventServiceImpl implements PublicEventService {
    private final EventStorage eventStorage;
    private final EventMapper eventMapper;

    @Override
    @Transactional
    public List<EventByIdOutputDto> getAllEvents(String text, List<Long> categories,
                                                 Boolean paid, LocalDateTime rangeStart,
                                                 LocalDateTime rangeEnd, Boolean onlyAvailable,
                                                 String sort, Integer from, Integer size, HttpServletRequest servletRequest) {
        if (rangeEnd != null && rangeStart != null) {
            if (rangeEnd.isBefore(rangeStart)) {
                throw new ValidException("Start date is before end date", HttpStatus.BAD_REQUEST);
            }
        }

        Pageable pageable = PageRequest.of(from / size, size);
        List<Event> events = eventStorage.findAllByOptionsForPublic(text, categories, paid, rangeStart, rangeEnd, pageable)
                .getContent();
        if (onlyAvailable) {
            events.removeIf(event -> event.getConfirmedRequests().equals(event.getParticipantLimit()));
        }
        if (sort != null) {
            if (sort.equals("VIEWS")) {
                events = events.stream()
                        .sorted(Comparator.comparing(Event::getViews).reversed())
                        .collect(Collectors.toList());
                return eventMapper.toEventByIdOutputDtoList(events);

            } else if (sort.equals("EVENT_DATE")) {
                events = events.stream()
                        .sorted(Comparator.comparing(Event::getEventDate).reversed())
                        .collect(Collectors.toList());
                return eventMapper.toEventByIdOutputDtoList(events);
            } else {
                throw new ValidException("Invalid sort parameter", HttpStatus.BAD_REQUEST);
            }
        }
        for (Event event : events) {
            event.setViews(event.getViews() + 1);
        }
        eventStorage.saveAll(events);
        return eventMapper.toEventByIdOutputDtoList(events);
    }

    @Override
    @Transactional
    public EventOutputDto getEventById(Long id, HttpServletRequest servletRequest) {
        Event event = eventStorage.findById(id).orElseThrow(() ->
                new ValidException("Event was not found", HttpStatus.NOT_FOUND));
        if (event.getPublishedOn() == null) {
            throw new ValidException("Event was not published", HttpStatus.NOT_FOUND);
        }
//        statisticsClient.createHit(StatisticsInputHitDto.builder()
//                .app("main-service")
//                .ip("/events/" + id)
//                .uri(servletRequest.getRequestURI())
//                .timestamp(LocalDateTime.now())
//                .build());

        event.setViews(event.getViews() + 1);
        eventStorage.save(event);
        return eventMapper.toEventOutputDto(event);
    }
}
