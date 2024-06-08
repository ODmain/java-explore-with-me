package ru.practicum.explore_with_me.dto.location;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationDto {
    @NotNull
    Double lat;
    @NotNull
    Double lon;
}
