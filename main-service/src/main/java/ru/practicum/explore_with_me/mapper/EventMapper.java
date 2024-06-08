package ru.practicum.explore_with_me.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.explore_with_me.dto.category.CategoryOutputDto;
import ru.practicum.explore_with_me.dto.event.EventOutputDto;
import ru.practicum.explore_with_me.dto.event.NewEventDto;
import ru.practicum.explore_with_me.dto.location.LocationDto;
import ru.practicum.explore_with_me.dto.user.UserOutputDto;
import ru.practicum.explore_with_me.model.Category;
import ru.practicum.explore_with_me.model.Event;
import ru.practicum.explore_with_me.model.Location;
import ru.practicum.explore_with_me.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "category")
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "initiator", source = "initiator")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "participantLimit", source = "newEventDto.participantLimit", defaultValue = "0")
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "requestModeration", source = "newEventDto.requestModeration", defaultValue = "true")
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "state", defaultValue = "PENDING")
    @Mapping(target = "views", ignore = true)
    Event toEventFromNew(NewEventDto newEventDto, Category category, Location location, User initiator);

    @Mapping(target = "category", source = "category")
    @Mapping(target = "initiator", source = "initiator")
    @Mapping(target = "location", source = "location")
    EventOutputDto toEventOutputDto(Event event, CategoryOutputDto category, UserOutputDto user, LocationDto location);

    List<EventOutputDto> toEventOutputDtoList(List<Event> events, CategoryOutputDto category, UserOutputDto user, LocationDto location);
}
