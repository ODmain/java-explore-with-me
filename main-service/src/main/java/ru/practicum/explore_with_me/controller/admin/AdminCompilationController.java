package ru.practicum.explore_with_me.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.dto.compilation.CompilationInputDto;
import ru.practicum.explore_with_me.dto.compilation.CompilationOutputDto;
import ru.practicum.explore_with_me.dto.compilation.CompilationUpdateDto;
import ru.practicum.explore_with_me.service.api.admin.AdminCompilationService;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/compilations")
public class AdminCompilationController {
    private final AdminCompilationService adminCompilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationOutputDto addCompilation(@RequestBody @Valid CompilationInputDto compilationInputDto) {
        return adminCompilationService.addCompilation(compilationInputDto);
    }

    @PatchMapping("/{compId}")
    public CompilationOutputDto updateCompilation(@PathVariable Long compId,
                                                  @RequestBody @Valid CompilationUpdateDto compilationUpdateDto) {
        return adminCompilationService.updateCompilation(compId, compilationUpdateDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        adminCompilationService.deleteCompilation(compId);
    }
}
