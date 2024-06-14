package ru.practicum.service.api.pub;

import ru.practicum.dto.compilation.CompilationOutputDto;

import java.util.List;

public interface PublicCompilationService {
    List<CompilationOutputDto> getCompilations(Boolean pinned, Integer from, Integer size);

    CompilationOutputDto getCompilationById(Long compId);
}
