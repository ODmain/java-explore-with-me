package ru.practicum.dto.compilation;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationUpdateDto {
    List<Long> events;
    Boolean pinned;
    @Size(min = 1, max = 50)
    String title;
}
