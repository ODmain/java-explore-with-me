package ru.practicum.dto.compilation;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.dto.event.EventByIdOutputDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationOutputDto {
    List<EventByIdOutputDto> events;
    Long id;
    Boolean pinned;
    String title;
}
