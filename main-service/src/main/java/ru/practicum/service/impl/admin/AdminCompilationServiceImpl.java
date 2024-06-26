package ru.practicum.service.impl.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.compilation.CompilationInputDto;
import ru.practicum.dto.compilation.CompilationOutputDto;
import ru.practicum.dto.compilation.CompilationUpdateDto;
import ru.practicum.dto.event.EventByIdOutputDto;
import ru.practicum.exception.ValidException;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.service.api.admin.AdminCompilationService;
import ru.practicum.storage.CompilationStorage;
import ru.practicum.storage.EventStorage;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationStorage compilationStorage;
    private final EventStorage eventStorage;
    private final EventMapper eventMapper;
    private final CompilationMapper compilationMapper;

    @Override
    @Transactional
    public CompilationOutputDto addCompilation(CompilationInputDto compilationInputDto) {
        List<Event> events = new ArrayList<>();
        if (compilationInputDto.getEvents() != null && !compilationInputDto.getEvents().isEmpty()) {
            events = eventStorage.findAllByIdIn(compilationInputDto.getEvents());
        }
        List<EventByIdOutputDto> eventByIdOutputDto = eventMapper.toEventByIdOutputDtoList(events);
        return compilationMapper.toCompilationDto(compilationStorage
                .save(compilationMapper.toCompilationFromDto(compilationInputDto, events)), eventByIdOutputDto);
    }

    @Override
    @Transactional
    public CompilationOutputDto updateCompilation(Long compId, CompilationUpdateDto compilationUpdateDto) {
        Compilation compilation = checkCompilation(compId);
        if (compilationUpdateDto.getEvents() != null && !compilationUpdateDto.getEvents().isEmpty()) {
            compilation.setEvents(eventStorage.findAllByIdIn(compilationUpdateDto.getEvents()));
        }
        if (compilationUpdateDto.getTitle() != null) {
            compilation.setTitle(compilationUpdateDto.getTitle());
        }
        if (compilationUpdateDto.getPinned() != null) {
            compilation.setPinned(compilationUpdateDto.getPinned());
        }
        List<EventByIdOutputDto> eventByIdOutputDto = eventMapper.toEventByIdOutputDtoList(compilation.getEvents());
        compilationStorage.save(compilation);
        return compilationMapper.toCompilationDto(compilation, eventByIdOutputDto);

    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        Compilation compilation = checkCompilation(compId);
        compilationStorage.deleteById(compilation.getId());
    }

    private Compilation checkCompilation(Long compId) {
        return compilationStorage.findById(compId).orElseThrow(() ->
                new ValidException("Compilation was not found", HttpStatus.NOT_FOUND));
    }
}
