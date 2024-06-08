package ru.practicum.explore_with_me.dto.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventUpdateRequestInputDto {
    List<Long> requestsIds;
    String status;
}
