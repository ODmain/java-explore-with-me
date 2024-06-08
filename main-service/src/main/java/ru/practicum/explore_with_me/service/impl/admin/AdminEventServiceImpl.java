package ru.practicum.explore_with_me.service.impl.admin;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.dto.event.EventByIdOutputDto;
import ru.practicum.explore_with_me.dto.event.EventOutputDto;
import ru.practicum.explore_with_me.dto.event.UpdateEventDto;
import ru.practicum.explore_with_me.service.api.admin.AdminEventService;
import ru.practicum.explore_with_me.storage.EventStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminEventServiceImpl implements AdminEventService {
    private final EventStorage eventStorage;

    @Override
    @Transactional
    public EventOutputDto updateEvent(Long eventId, UpdateEventDto updateEventDto) {
        return null;
    }

    @Override
    public EventByIdOutputDto getEvents(List<Long> users, List<String> states, List<Long> categories, String rangeStart,
                                        String rangeEnd, Integer from, Integer size) {
        return null;
    }
}
