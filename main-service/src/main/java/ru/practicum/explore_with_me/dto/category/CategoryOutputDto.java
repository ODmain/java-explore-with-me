package ru.practicum.explore_with_me.dto.category;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryOutputDto {
    Long id;
    String name;
}
