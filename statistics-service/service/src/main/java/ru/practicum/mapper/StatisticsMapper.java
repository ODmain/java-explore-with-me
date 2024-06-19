package ru.practicum.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.dto.StatisticsInputHitDto;
import ru.practicum.model.Statistics;

@Mapper(componentModel = "spring")
public interface StatisticsMapper {
    @Mapping(target = "id", ignore = true)
    Statistics toStatistics(StatisticsInputHitDto statisticsInputHitDto);
}
