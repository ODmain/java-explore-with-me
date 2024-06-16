package ru.practicum.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.dto.StatisticsOutputDto;
import ru.practicum.model.Statistics;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsStorage extends JpaRepository<Statistics, Long> {

    @Query("SELECT new ru.practicum.dto.StatisticsOutputDto(s.app, s.uri, COUNT(DISTINCT s.ip)) " +
            "FROM Statistics s " +
            "WHERE s.uri IN :uris " +
            "AND s.timestamp BETWEEN :start AND :end " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT(DISTINCT s.ip) DESC")
    List<StatisticsOutputDto> findAllUniqueStatisticsByUrisBetweenStartAndEnd(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.dto.StatisticsOutputDto(s.app, s.uri, COUNT(s.ip)) " +
            "FROM Statistics s " +
            "WHERE s.uri IN :uris " +
            "AND s.timestamp BETWEEN :start AND :end " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT(s.ip) DESC")
    List<StatisticsOutputDto> findAllStatisticsByUrisBetweenStartAndEnd(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.dto.StatisticsOutputDto(s.app, s.uri, COUNT(DISTINCT s.ip)) " +
            "FROM Statistics s " +
            "WHERE s.timestamp BETWEEN :start AND :end " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT(DISTINCT s.ip) DESC")
    List<StatisticsOutputDto> findAllUniqueStatisticsBetweenStartAndEnd(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.dto.StatisticsOutputDto(s.app, s.uri, COUNT(s.ip)) " +
            "FROM Statistics s " +
            "WHERE s.timestamp BETWEEN :start AND :end " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT(s.ip) DESC")
    List<StatisticsOutputDto> findAllStatisticsBetweenStartAndEnd(LocalDateTime start, LocalDateTime end);

}
