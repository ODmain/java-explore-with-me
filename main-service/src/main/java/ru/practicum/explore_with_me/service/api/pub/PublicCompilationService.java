package ru.practicum.explore_with_me.service.api.pub;

import ru.practicum.explore_with_me.dto.compilation.CompilationOutputDto;

import java.util.List;

public interface PublicCompilationService {
    List<CompilationOutputDto> getCompilations(Boolean pinned, Integer from, Integer size);

    CompilationOutputDto getCompilationById(Long compId);
}
