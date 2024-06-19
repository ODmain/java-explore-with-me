package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.location.LocationDto;
import ru.practicum.mapper.LocationMapper;
import ru.practicum.model.Location;
import ru.practicum.service.api.LocationService;
import ru.practicum.storage.LocationStorage;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationServiceImpl implements LocationService {
    private final LocationStorage locationStorage;
    private final LocationMapper locationMapper;

    @Override
    @Transactional
    public Location addLocation(LocationDto location) {
        return locationStorage.save(locationMapper.toLocationFromDto(location));
    }
}
