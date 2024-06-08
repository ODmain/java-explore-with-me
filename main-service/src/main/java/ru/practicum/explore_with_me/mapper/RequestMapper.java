package ru.practicum.explore_with_me.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explore_with_me.dto.request.RequestOutputDto;
import ru.practicum.explore_with_me.model.Request;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    RequestOutputDto toRequestOutputDto(Request request);

    List<RequestOutputDto> toRequestOutputDtoList(List<Request> requests);

}
