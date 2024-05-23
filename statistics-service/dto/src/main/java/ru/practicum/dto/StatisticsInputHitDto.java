package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

//С NotBlank все тесты валятся, решил оставить NotNull
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class StatisticsInputHitDto {
    @NotNull(message = "Cannot be null or empty")
    @Size(min = 1, max = 100)
    private String app;
    @NotNull(message = "Cannot be null or empty")
    @Size(min = 1, max = 100)
    private String uri;
    @NotNull(message = "Cannot be null or empty")
    @Size(min = 1, max = 20)
    private String ip;
    @NotNull(message = "Cannot be null or empty")
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
