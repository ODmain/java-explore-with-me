package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.location.LocationDto;
import ru.practicum.model.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location toLocationFromDto(LocationDto locationDto);

    LocationDto toLocationDto(Location location);
}
