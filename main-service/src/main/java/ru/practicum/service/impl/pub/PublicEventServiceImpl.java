package ru.practicum.service.impl.pub;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatisticsClient;
import ru.practicum.dto.StatisticsInputHitDto;
import ru.practicum.dto.event.EventByIdOutputDto;
import ru.practicum.dto.event.EventOutputDto;
import ru.practicum.exception.ValidException;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Event;
import ru.practicum.service.api.pub.PublicEventService;
import ru.practicum.storage.EventStorage;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicEventServiceImpl implements PublicEventService {
    private final EventStorage eventStorage;
    private final EventMapper eventMapper;
    private final StatisticsClient statisticsClient;
    private final ObjectMapper objectMapper;

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

        if (rangeStart == null) {
            rangeStart = LocalDateTime.now().minusYears(100);
        }

        if (rangeEnd == null) {
            rangeEnd = LocalDateTime.now().plusYears(100);
        }

        Pageable pageable = PageRequest.of(from / size, size);
        List<Event> events = eventStorage.findAllByOptionsForPublic(text, categories, paid, rangeStart, rangeEnd, pageable);
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
        event.setViews(event.getViews() + 1);
        eventStorage.save(event);
        statisticsClient.createHit(StatisticsInputHitDto.builder()
                .app("main-service")
                .ip("/events/" + id)
                .uri(servletRequest.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build());

        return eventMapper.toEventOutputDto(event);
    }

//    private Map<Long, Long> getEventStatisticMap(LocalDateTime rangeStart, LocalDateTime rangeEnd, List<Long> eventsIds) {
//        String rangeStartUpdate = rangeStart.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//        String rangeEndUpdate = rangeEnd.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//
//        List<String> uris = eventsIds.stream()
//                .map(id -> "/events/" + id)
//                .collect(Collectors.toList());
//
//        ResponseEntity<Object> stats = statisticsClient.getStatistics(rangeStartUpdate, rangeEndUpdate, uris, true);
//        List<StatisticsOutputDto> statisticDto;
//        statisticDto = objectMapper.convertValue(stats.getBody(), new TypeReference<>() {});
//
//        return statisticDto.stream()
//                .collect(Collectors.toMap(
//                        dto -> extractIdFromUri(dto.getUri()),
//                        StatisticsOutputDto::getHits,
//                        Long::sum)
//                );
//    }
//
//    private Long extractIdFromUri(String uri) {
//        String[] parts = uri.split("/");
//        return Long.parseLong(parts[parts.length - 1]);
//    }
}
