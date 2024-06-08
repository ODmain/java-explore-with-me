package ru.practicum.explore_with_me.dto.event;


import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.dto.location.LocationDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventDto {
    @NotNull
    String annotation;
    @NotNull
    Long category;
    @NotNull
    String description;
    @NotNull
    LocalDateTime eventDate;
    @NotNull
    LocationDto location;
    @NotNull
    Boolean paid;
    @NotNull
    Long participantLimit;
    @NotNull
    Boolean requestModeration;
    @NotNull
    String title;
}
