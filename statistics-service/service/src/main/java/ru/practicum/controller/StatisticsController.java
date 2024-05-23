package ru.practicum.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.StatisticsInputHitDto;
import ru.practicum.dto.StatisticsOutputDto;
import ru.practicum.exception.ValidException;
import ru.practicum.service.StatisticsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
public class StatisticsController {
    private final StatisticsService statisticsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void createHit(@Valid @RequestBody StatisticsInputHitDto statisticsInputHitDto) {
        log.info("Trying to create hit.");
        statisticsService.createHit(statisticsInputHitDto);
    }

    @GetMapping("/stats")
    public List<StatisticsOutputDto> getStatistics(@RequestParam String start,
                                                   @RequestParam String end,
                                                   @RequestParam(required = false) List<String> uris,
                                                   @RequestParam(defaultValue = "false") Boolean unique) {
        LocalDateTime newStart = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime newEnd = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        validDate(newStart, newEnd);
        log.info("Trying to get statistics.");
        return statisticsService.getStatistics(newStart, newEnd, uris, unique);
    }

    private void validDate(LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)) {
            throw new ValidException("Incorrect parameters passed.", HttpStatus.BAD_REQUEST);
        }
    }
}