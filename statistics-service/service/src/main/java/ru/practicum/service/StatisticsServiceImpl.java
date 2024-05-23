package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.StatisticsInputHitDto;
import ru.practicum.dto.StatisticsOutputDto;
import ru.practicum.mapper.StatisticsMapper;
import ru.practicum.storage.StatisticsStorage;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsMapper statisticsMapper;
    private final StatisticsStorage statisticsStorage;

    @Override
    public void createHit(StatisticsInputHitDto statisticsInputHitDto) {
        statisticsStorage.save(statisticsMapper.toStatistics(statisticsInputHitDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatisticsOutputDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (uris == null || uris.isEmpty()) {
            if (unique) {
                return statisticsStorage.findAllUniqueStatisticsBetweenStartAndEnd(start, end);
            } else {
                return statisticsStorage.findAllStatisticsBetweenStartAndEnd(start, end);
            }
        } else {
            if (unique) {
                return statisticsStorage.findAllUniqueStatisticsByUrisBetweenStartAndEnd(start, end, uris);
            } else {
                return statisticsStorage.findAllStatisticsByUrisBetweenStartAndEnd(start, end, uris);
            }
        }
    }
}
