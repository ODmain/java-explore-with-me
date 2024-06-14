package ru.practicum.explore_with_me.dto.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.constant.Status;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventUpdateRequestInputDto {
    @NotNull
    List<Long> requestIds;
    @NotNull
    Status status;
}
