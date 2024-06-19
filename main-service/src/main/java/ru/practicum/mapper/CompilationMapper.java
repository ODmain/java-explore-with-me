package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.dto.compilation.CompilationInputDto;
import ru.practicum.dto.compilation.CompilationOutputDto;
import ru.practicum.dto.event.EventByIdOutputDto;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface CompilationMapper {

    @Mapping(target = "events", source = "events")
    CompilationOutputDto toCompilationDto(Compilation compilation, List<EventByIdOutputDto> events);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", source = "events")
    @Mapping(target = "pinned", defaultValue = "false")
    Compilation toCompilationFromDto(CompilationInputDto compilationInputDto, List<Event> events);

    List<CompilationOutputDto> toCompilationDtoList(List<Compilation> compilations);
}
