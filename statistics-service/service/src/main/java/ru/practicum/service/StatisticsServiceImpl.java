package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.StatisticsInputHitDto;
import ru.practicum.dto.StatisticsOutputDto;
import ru.practicum.mapper.StatisticsMapper;
import ru.practicum.storage.StatisticsStorage;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        statisticsStorage.save(statisticsMapper.toStatistics(statisticsInputHitDto));
    }

    @Override
    public List<StatisticsOutputDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        List<String> uri = new ArrayList<>();
        for (String s : uris) {
            uri.add(s.replace("[", "").replace("]", ""));
        }
        if (uris.isEmpty()) {
            if (unique) {
                return statisticsStorage.findAllUniqueStatisticsBetweenStartAndEnd(start, end);
            } else {
                return statisticsStorage.findAllStatisticsBetweenStartAndEnd(start, end);
            }
        } else {
            if (unique) {
                return statisticsStorage.findAllUniqueStatisticsByUrisBetweenStartAndEnd(start, end, uri);
            } else {
                return statisticsStorage.findAllStatisticsByUrisBetweenStartAndEnd(start, end, uri);
            }
        }
    }
}
