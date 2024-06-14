package ru.practicum.explore_with_me.service.api.admin;

import ru.practicum.explore_with_me.dto.compilation.CompilationInputDto;
import ru.practicum.explore_with_me.dto.compilation.CompilationOutputDto;
import ru.practicum.explore_with_me.dto.compilation.CompilationUpdateDto;

public interface AdminCompilationService {
    CompilationOutputDto addCompilation(CompilationInputDto compilationInputDto);

    CompilationOutputDto updateCompilation(Long compId, CompilationUpdateDto compilationUpdateDto);

    void deleteCompilation(Long compId);

}
