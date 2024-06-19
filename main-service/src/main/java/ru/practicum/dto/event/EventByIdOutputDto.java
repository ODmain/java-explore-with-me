package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.dto.category.CategoryOutputDto;
import ru.practicum.dto.user.UserOutputDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventByIdOutputDto {
    String annotation;
    CategoryOutputDto category;
    Long confirmedRequests;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime eventDate;
    Long id;
    UserOutputDto initiator;
    Boolean paid;
    String title;
    Long views;
}
