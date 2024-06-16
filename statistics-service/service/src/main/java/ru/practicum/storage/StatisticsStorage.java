package ru.practicum.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.dto.StatisticsOutputDto;
import ru.practicum.model.Statistics;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsStorage extends JpaRepository<Statistics, Long> {

    @Query("SELECT new ru.practicum.dto.StatisticsOutputDto (eph.app, eph.uri, count(distinct(eph.ip))) " +
            "from Statistics as eph " +
            "where eph.timestamp >= ?1 and eph.timestamp <= ?2 " +
            "and eph.uri in ?3 " +
            "group by eph.app, eph.uri " +
            "order by count(distinct(eph.ip)) desc ")
    List<StatisticsOutputDto> findAllUniqueStatisticsByUrisBetweenStartAndEnd(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.dto.StatisticsOutputDto (eph.app, eph.uri, count(eph.ip)) " +
            "from Statistics as eph " +
            "where eph.timestamp >= ?1 and eph.timestamp <= ?2 " +
            "and eph.uri in ?3 " +
            "group by eph.app, eph.uri " +
            "order by count(eph.ip) desc ")
    List<StatisticsOutputDto> findAllStatisticsByUrisBetweenStartAndEnd(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.dto.StatisticsOutputDto (eph.app, eph.uri, count(distinct(eph.ip))) " +
            "from Statistics as eph " +
            "where eph.timestamp >= ?1 and eph.timestamp <= ?2 " +
            "group by eph.app, eph.uri " +
            "order by count(distinct(eph.ip)) desc ")
    List<StatisticsOutputDto> findAllUniqueStatisticsBetweenStartAndEnd(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.dto.StatisticsOutputDto (eph.app, eph.uri, count(eph.ip)) " +
            "from Statistics as eph " +
            "where eph.timestamp >= ?1 and eph.timestamp <= ?2 " +
            "group by eph.app, eph.uri " +
            "order by count(eph.ip) desc ")
    List<StatisticsOutputDto> findAllStatisticsBetweenStartAndEnd(LocalDateTime start, LocalDateTime end);

}
