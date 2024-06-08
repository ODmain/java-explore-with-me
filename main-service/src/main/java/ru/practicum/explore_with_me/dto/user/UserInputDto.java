package ru.practicum.explore_with_me.dto.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInputDto {
    @NotNull(message = "Cannot be null or empty")
    String email;
    @NotNull(message = "Cannot be null or empty")
    String name;
}