package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.dto.event.EventByIdOutputDto;
import ru.practicum.dto.event.EventOutputDto;
import ru.practicum.dto.event.NewEventDto;
import ru.practicum.model.Event;

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
