package ru.practicum.explore_with_me.service.api;

import ru.practicum.explore_with_me.dto.location.LocationDto;
import ru.practicum.explore_with_me.model.Location;

public interface LocationService {
    Location addLocation(LocationDto locationInputDto);
}
