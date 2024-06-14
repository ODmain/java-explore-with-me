package ru.practicum.explore_with_me.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.explore_with_me.dto.request.RequestOutputDto;
import ru.practicum.explore_with_me.model.Request;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    RequestOutputDto toRequestOutputDto(Request request);

    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    List<RequestOutputDto> toRequestOutputDtoList(List<Request> requests);

}
