package ru.practicum.explore_with_me.dto.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.model.Location;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventDto {
    String annotation;
    Long category;
    String description;
    String eventDate;
    Location location;
    Boolean paid;
    Long participantLimit;
    Boolean requestModeration;
    String title;
}
