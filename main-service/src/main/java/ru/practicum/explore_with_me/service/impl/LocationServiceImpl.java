package ru.practicum.explore_with_me.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.dto.location.LocationDto;
import ru.practicum.explore_with_me.mapper.LocationMapper;
import ru.practicum.explore_with_me.model.Location;
import ru.practicum.explore_with_me.service.api.LocationService;
import ru.practicum.explore_with_me.storage.LocationStorage;

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
