package ru.practicum.dto.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.dto.request.RequestOutputDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventUpdateRequestOutputDto {
    List<RequestOutputDto> confirmedRequests;
    List<RequestOutputDto> rejectedRequests;
}
