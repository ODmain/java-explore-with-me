package ru.practicum.service;

import ru.practicum.dto.StatisticsInputHitDto;
import ru.practicum.dto.StatisticsOutputDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsService {
    void createHit(StatisticsInputHitDto statisticsInputHitDto);

    List<StatisticsOutputDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
