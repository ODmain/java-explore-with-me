package ru.practicum.service.api;

import ru.practicum.dto.location.LocationDto;
import ru.practicum.model.Location;

public interface LocationService {
    Location addLocation(LocationDto locationInputDto);
}
