package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class StatisticsInputHitDto {
    @NotBlank(message = "Cannot be null or empty")
    @Size(min = 1, max = 40)
    private String app;
    @NotBlank(message = "Cannot be null or empty")
    @Size(min = 1, max = 40)
    private String uri;
    @NotBlank(message = "Cannot be null or empty")
    @Size(min = 1, max = 40)
    private String ip;
    @NotBlank(message = "Cannot be null or empty")
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
