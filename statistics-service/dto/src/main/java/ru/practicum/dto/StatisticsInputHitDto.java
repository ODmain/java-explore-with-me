package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatisticsInputHitDto {
    @NotNull(message = "Cannot be null or empty")
    @Size(min = 1, max = 100)
    String app;
    @NotNull(message = "Cannot be null or empty")
    @Size(min = 1, max = 100)
    String uri;
    @NotNull(message = "Cannot be null or empty")
    @Size(min = 1, max = 20)
    String ip;
    @NotNull(message = "Cannot be null or empty")
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp;
}
