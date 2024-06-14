package ru.practicum.explore_with_me.dto.compilation;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationInputDto {
    List<Long> events;
    Boolean pinned;
    @NotBlank
    @Size(min = 1, max = 50)
    String title;
}
