package ru.practicum.explore_with_me.dto.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.dto.category.CategoryOutputDto;
import ru.practicum.explore_with_me.dto.user.UserOutputDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventByIdOutputDto {
    String annotation;
    CategoryOutputDto category;
    Long confirmedRequests;
    String eventDate;
    Long id;
    UserOutputDto initiator;
    Boolean paid;
    String title;
    Long views;
}
