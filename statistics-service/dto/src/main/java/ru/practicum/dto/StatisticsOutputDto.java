package ru.practicum.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class StatisticsOutputDto {
    private String app;
    private String uri;
    private Long hits;
}
