package ru.practicum.dto.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.constant.Status;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventUpdateRequestInputDto {
    List<Long> requestIds;
    Status status;
}
