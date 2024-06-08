package ru.practicum.explore_with_me.service.impl.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.dto.compilation.CompilationInputDto;
import ru.practicum.explore_with_me.dto.compilation.CompilationOutputDto;
import ru.practicum.explore_with_me.service.api.admin.AdminCompilationService;
import ru.practicum.explore_with_me.storage.CompilationStorage;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationStorage compilationStorage;

    @Override
    @Transactional
    public CompilationOutputDto addCompilation(CompilationInputDto compilationInputDto) {
        return null;
    }

    @Override
    @Transactional
    public CompilationOutputDto updateCompilation(Long compId, CompilationInputDto compilationInputDto) {
        return null;
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {

    }
}
