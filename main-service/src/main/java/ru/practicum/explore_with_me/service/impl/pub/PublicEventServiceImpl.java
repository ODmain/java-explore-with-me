package ru.practicum.explore_with_me.service.impl.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatisticsClient;
import ru.practicum.dto.StatisticsInputHitDto;
import ru.practicum.explore_with_me.dto.event.EventByIdOutputDto;
import ru.practicum.explore_with_me.dto.event.EventOutputDto;
import ru.practicum.explore_with_me.exception.ValidException;
import ru.practicum.explore_with_me.mapper.EventMapper;
import ru.practicum.explore_with_me.model.Event;
import ru.practicum.explore_with_me.service.api.pub.PublicEventService;
import ru.practicum.explore_with_me.storage.EventStorage;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicEventServiceImpl implements PublicEventService {
    private final EventStorage eventStorage;
    private final EventMapper eventMapper;
    //private final StatisticsClient statisticsClient;

    @Override
    @Transactional
    public List<EventByIdOutputDto> getAllEvents(String text, List<Long> categories,
                                                 Boolean paid, LocalDateTime rangeStart,
                                                 LocalDateTime rangeEnd, Boolean onlyAvailable,
                                                 String sort, Integer from, Integer size, HttpServletRequest servletRequest) {
        if (rangeEnd.isBefore(rangeStart)) {
            throw new ValidException("Start date is before end date", HttpStatus.BAD_REQUEST);
        }
//        statisticsClient.createHit(StatisticsInputHitDto.builder()
//                .app("main-service")
//                .ip(servletRequest.getRemoteAddr())
//                .uri(servletRequest.getRequestURI())
//                .timestamp(LocalDateTime.now())
//                .build());

//        Pageable pageable = PageRequest.of(from / size, size);
//        List<Event> events = eventStorage.findEventsByOptions(users, states, categories, rangeStart, rangeEnd, pageable)
//                .getContent();
//        return eventMapper.toEventByIdOutputDtoList(events);

        return new ArrayList<>();
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
