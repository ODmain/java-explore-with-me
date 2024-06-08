package ru.practicum.explore_with_me.service.api.admin;

import ru.practicum.explore_with_me.dto.compilation.CompilationInputDto;
import ru.practicum.explore_with_me.dto.compilation.CompilationOutputDto;

public interface AdminCompilationService {
    CompilationOutputDto addCompilation(CompilationInputDto compilationInputDto);

    CompilationOutputDto updateCompilation(Long compId, CompilationInputDto compilationInputDto);

    void deleteCompilation(Long compId);

}
