package ru.practicum.explore_with_me.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.explore_with_me.dto.event.EventByIdOutputDto;
import ru.practicum.explore_with_me.dto.event.EventOutputDto;
import ru.practicum.explore_with_me.dto.event.NewEventDto;
import ru.practicum.explore_with_me.model.Event;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, LocationMapper.class, UserMapper.class})
public interface EventMapper {
    EventOutputDto toEventOutputDto(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "paid", defaultValue = "false")
    @Mapping(target = "participantLimit", defaultValue = "0L")
    @Mapping(target = "requestModeration", defaultValue = "true")
    Event toEvent(NewEventDto newEventDto);

    EventByIdOutputDto toEventByIdOutputDto(Event event);

    List<EventOutputDto> toEventOutputDtoList(List<Event> events);

    List<EventByIdOutputDto> toEventByIdOutputDtoList(List<Event> event);
}
