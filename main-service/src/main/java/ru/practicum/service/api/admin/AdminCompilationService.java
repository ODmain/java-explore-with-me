package ru.practicum.service.api.admin;

import ru.practicum.dto.compilation.CompilationInputDto;
import ru.practicum.dto.compilation.CompilationOutputDto;
import ru.practicum.dto.compilation.CompilationUpdateDto;

public interface AdminCompilationService {
    CompilationOutputDto addCompilation(CompilationInputDto compilationInputDto);

    CompilationOutputDto updateCompilation(Long compId, CompilationUpdateDto compilationUpdateDto);

    void deleteCompilation(Long compId);

}
