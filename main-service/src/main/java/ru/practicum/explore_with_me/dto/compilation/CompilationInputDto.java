package ru.practicum.explore_with_me.dto.compilation;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationInputDto {
    List<Long> events;
    Boolean pinned;
    String title;
}
