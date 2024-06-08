package ru.practicum.explore_with_me.dto.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.constant.State;
import ru.practicum.explore_with_me.dto.category.CategoryOutputDto;
import ru.practicum.explore_with_me.dto.location.LocationDto;
import ru.practicum.explore_with_me.dto.user.UserOutputDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventOutputDto {
    String annotation;
    CategoryOutputDto category;
    Long confirmedRequests;
    LocalDateTime createdOn;
    String description;
    LocalDateTime eventDate;
    Long id;
    UserOutputDto initiator;
    LocationDto location;
    Boolean paid;
    Long participantLimit;
    LocalDateTime publishedOn;
    Boolean requestModeration;
    State state;
    String title;
    Long views;
}
