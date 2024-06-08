package ru.practicum.explore_with_me.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explore_with_me.dto.location.LocationDto;
import ru.practicum.explore_with_me.model.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location toLocationFromDto(LocationDto locationDto);

    LocationDto toLocationDto(Location location);
}
