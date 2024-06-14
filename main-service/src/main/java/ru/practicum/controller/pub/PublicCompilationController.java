package ru.practicum.controller.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.compilation.CompilationOutputDto;
import ru.practicum.service.api.pub.PublicCompilationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationController {
    private final PublicCompilationService compilationService;

    @GetMapping
    public List<CompilationOutputDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                      @RequestParam(defaultValue = "0") Integer from,
                                                      @RequestParam(defaultValue = "10") Integer size) {
        return compilationService.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationOutputDto getCompilationById(@PathVariable Long compId) {
        return compilationService.getCompilationById(compId);
    }
}
