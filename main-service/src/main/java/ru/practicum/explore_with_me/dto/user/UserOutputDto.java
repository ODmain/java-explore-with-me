package ru.practicum.explore_with_me.dto.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserOutputDto {
    Long id;
    String email;
    String name;
}
