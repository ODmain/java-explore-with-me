package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.constant.State;
import ru.practicum.dto.category.CategoryOutputDto;
import ru.practicum.dto.location.LocationDto;
import ru.practicum.dto.user.UserOutputDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventOutputDto {
    Long id;
    String annotation;
    CategoryOutputDto category;
    Long confirmedRequests;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime createdOn;
    String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime eventDate;
    UserOutputDto initiator;
    LocationDto location;
    Boolean paid;
    Long participantLimit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime publishedOn;
    Boolean requestModeration;
    State state;
    String title;
    Long views;
}
