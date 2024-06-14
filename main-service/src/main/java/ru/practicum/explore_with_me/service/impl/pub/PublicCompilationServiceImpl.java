package ru.practicum.explore_with_me.service.impl.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.dto.compilation.CompilationOutputDto;
import ru.practicum.explore_with_me.exception.ValidException;
import ru.practicum.explore_with_me.mapper.CompilationMapper;
import ru.practicum.explore_with_me.mapper.EventMapper;
import ru.practicum.explore_with_me.model.Compilation;
import ru.practicum.explore_with_me.service.api.pub.PublicCompilationService;
import ru.practicum.explore_with_me.storage.CompilationStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicCompilationServiceImpl implements PublicCompilationService {
    private final CompilationStorage compilationStorage;
    private final CompilationMapper compilationMapper;
    private final EventMapper eventMapper;

    @Override
    public List<CompilationOutputDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        if (pinned != null) {
            return compilationMapper.toCompilationDtoList(compilationStorage.findAllByPinned(pinned, pageable).getContent());
        } else {
            return compilationMapper.toCompilationDtoList(compilationStorage.findAll(pageable).getContent());
        }
    }

    @Override
    public CompilationOutputDto getCompilationById(Long compId) {
        Compilation compilation = compilationStorage.findById(compId).orElseThrow(() ->
                new ValidException("Compilation was not found", HttpStatus.NOT_FOUND));
        return compilationMapper.toCompilationDto(compilation, eventMapper.toEventByIdOutputDtoList(compilation.getEvents()));
    }
}
