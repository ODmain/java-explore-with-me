package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.StatisticsInputHitDto;
import ru.practicum.model.Statistics;

@Mapper(componentModel = "spring")
public interface StatisticsMapper {
    Statistics toStatistics(StatisticsInputHitDto statisticsInputHitDto);
}
