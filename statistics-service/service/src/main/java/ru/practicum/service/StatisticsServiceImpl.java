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
@Transactional(readOnly = true)
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsStorage statisticsStorage;
    private final StatisticsMapper statisticsMapper;

    @Override
    @Transactional
    public void createHit(StatisticsInputHitDto statisticsInputHitDto) {
        System.err.println(statisticsInputHitDto);
        statisticsStorage.save(statisticsMapper.toStatistics(statisticsInputHitDto));
    }

    @Override
    public List<StatisticsOutputDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (uris == null || uris.isEmpty()) {
            if (unique) {
                return statisticsStorage.findAllUniqueStatisticsBetweenStartAndEnd(start, end);
            } else {
                return statisticsStorage.findAllStatisticsBetweenStartAndEnd(start, end);
            }
        } else {
            if (unique) {
                List<StatisticsOutputDto> statisticsOutputDtos = statisticsStorage.findAllUniqueStatisticsByUrisBetweenStartAndEnd(start, end, uris);
                System.err.println("serrr" + statisticsOutputDtos);
                return statisticsStorage.findAllUniqueStatisticsByUrisBetweenStartAndEnd(start, end, uris);
            } else {
                return statisticsStorage.findAllStatisticsByUrisBetweenStartAndEnd(start, end, uris);
            }
        }
    }
}
