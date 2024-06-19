package ru.practicum.service.impl.pub;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatisticsClient;
import ru.practicum.constant.Status;
import ru.practicum.dto.StatisticsInputHitDto;
import ru.practicum.dto.StatisticsOutputDto;
import ru.practicum.dto.event.EventByIdOutputDto;
import ru.practicum.dto.event.EventOutputDto;
import ru.practicum.exception.ValidException;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Event;
import ru.practicum.service.api.pub.PublicEventService;
import ru.practicum.storage.EventStorage;
import ru.practicum.storage.RequestStorage;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicEventServiceImpl implements PublicEventService {
    private final EventStorage eventStorage;
    private final EventMapper eventMapper;
    private final StatisticsClient statisticsClient;
    private final ObjectMapper objectMapper;
    private final RequestStorage requestStorage;

    @Override
    @Transactional
    public List<EventByIdOutputDto> getAllEvents(String text, List<Long> categories,
                                                 Boolean paid, LocalDateTime rangeStart,
                                                 LocalDateTime rangeEnd, Boolean onlyAvailable,
                                                 String sort, Integer from, Integer size, HttpServletRequest servletRequest) {
        statisticsClient.createHit(StatisticsInputHitDto.builder()
                .app("emw-main-service")
                .uri("/events")
                .ip(servletRequest.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build());

        if (rangeEnd != null && rangeStart != null) {
            if (rangeEnd.isBefore(rangeStart)) {
                throw new ValidException("Start date is before end date", HttpStatus.BAD_REQUEST);
            }
        }

        if (rangeStart == null) {
            rangeStart = LocalDateTime.now().minusYears(100);
        }

        if (rangeEnd == null) {
            rangeEnd = LocalDateTime.now().plusYears(100);
        }

        Pageable pageable = PageRequest.of(from / size, size);
        List<Event> events = eventStorage.findAllByOptionsForPublic(text, categories, paid, rangeStart, rangeEnd, pageable);
        Map<String, Long> stats = getEventViewsMap(events);
        for (Event event : events) {
            event.setConfirmedRequests(requestStorage.countRequestByEventIdAndStatus(event.getId(), Status.CONFIRMED));
            Long views = stats.getOrDefault("/events/" + event.getId(), 0L);
            event.setViews(views);
            eventStorage.save(event);
        }
        if (onlyAvailable) {
            events.removeIf(event -> event.getConfirmedRequests().equals(event.getParticipantLimit()));
        }
        if (sort != null) {
            if (sort.equals("VIEWS")) {
                events = events.stream()
                        .sorted(Comparator.comparing(Event::getViews).reversed()).limit(size)
                        .collect(Collectors.toList());
                return eventMapper.toEventByIdOutputDtoList(events);

            } else if (sort.equals("EVENT_DATE")) {
                events = events.stream()
                        .sorted(Comparator.comparing(Event::getEventDate).reversed()).limit(size)
                        .collect(Collectors.toList());
                return eventMapper.toEventByIdOutputDtoList(events);
            } else {
                throw new ValidException("Invalid sort parameter", HttpStatus.BAD_REQUEST);
            }
        }
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
        statisticsClient.createHit(StatisticsInputHitDto.builder()
                .app("main-service")
                .uri("/events/" + id)
                .ip(servletRequest.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build());

        List<Event> events = new ArrayList<>();
        events.add(event);
        Map<String, Long> stats = getEventViewsMap(events);
        event.setConfirmedRequests(requestStorage.countRequestByEventIdAndStatus(event.getId(), Status.CONFIRMED));
        Long views = stats.getOrDefault("/events/" + event.getId(), 0L);
        event.setViews(views);
        eventStorage.save(event);
        return eventMapper.toEventOutputDto(event);
    }

    private Map<String, Long> getEventViewsMap(List<Event> events) {
        List<String> uris = events.stream()
                .map(event -> "/events/" + event.getId())
                .collect(Collectors.toList());
        LocalDateTime startDate = LocalDateTime.now().minusYears(100);
        LocalDateTime endDate = LocalDateTime.now();

        ResponseEntity<Object> response = statisticsClient.getStatistics(startDate, endDate, uris, true);
        System.err.println(response.getBody());
        Map<String, Long> result = new HashMap<>();
        if (response.getStatusCode() == HttpStatus.OK) {
            List<StatisticsOutputDto> statistics = objectMapper.convertValue(response.getBody(), new TypeReference<List<StatisticsOutputDto>>() {
            });
            for (StatisticsOutputDto s : statistics) {
                result.put(s.getUri(), s.getHits());
            }
        }
        return result;
    }
}
