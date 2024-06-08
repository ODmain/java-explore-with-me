package ru.practicum.explore_with_me.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestOutputDto {
    LocalDateTime created;
    Long event;
    Long id;
    Long requester;
    String status;
}
