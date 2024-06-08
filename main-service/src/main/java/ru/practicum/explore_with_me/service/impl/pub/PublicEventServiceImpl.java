package ru.practicum.explore_with_me.service.impl.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.dto.event.EventByIdOutputDto;
import ru.practicum.explore_with_me.dto.event.EventOutputDto;
import ru.practicum.explore_with_me.service.api.pub.PublicEventService;
import ru.practicum.explore_with_me.storage.EventStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicEventServiceImpl implements PublicEventService {
    private final EventStorage eventStorage;

    @Override
    public List<EventOutputDto> getAllEvents(String text, List<Long> categories,
                                             Boolean paid, String rangeStart,
                                             String rangeEnd, Boolean onlyAvailable,
                                             String sort, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventByIdOutputDto getEventById(Long id) {
        return null;
    }
}
