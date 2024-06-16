package ru.practicum.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.StatisticsInputHitDto;
import ru.practicum.dto.StatisticsOutputDto;
import ru.practicum.exception.ValidException;
import ru.practicum.service.StatisticsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
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
    public List<StatisticsOutputDto> getStatistics(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                                   @RequestParam(required = false) List<String> uris,
                                                   @RequestParam(defaultValue = "false") Boolean unique) {
        validDate(start, end);
        log.info("Trying to get statistics.");
        return statisticsService.getStatistics(start, end, uris, unique);
    }

    private void validDate(LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)) {
            throw new ValidException("Incorrect parameters passed.", HttpStatus.BAD_REQUEST);
        }
    }
}