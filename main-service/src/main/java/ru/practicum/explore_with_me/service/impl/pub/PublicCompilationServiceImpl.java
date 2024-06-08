package ru.practicum.explore_with_me.service.impl.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.dto.compilation.CompilationOutputDto;
import ru.practicum.explore_with_me.service.api.pub.PublicCompilationService;
import ru.practicum.explore_with_me.storage.CompilationStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicCompilationServiceImpl implements PublicCompilationService {
    private final CompilationStorage compilationStorage;

    @Override
    public List<CompilationOutputDto> getCompilations(Boolean pinned, Integer from, Integer size) {

        return null;
    }

    @Override
    public CompilationOutputDto getCompilationById(Long compId) {
        return null;
    }
}
