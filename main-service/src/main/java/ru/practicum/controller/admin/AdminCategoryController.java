package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.CategoryInputDto;
import ru.practicum.dto.category.CategoryOutputDto;
import ru.practicum.service.api.admin.AdminCategoryService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryOutputDto addCategory(@Valid @RequestBody CategoryInputDto categoryInputDto) {
        return adminCategoryService.addCategory(categoryInputDto);
    }

    @PatchMapping("/{catId}")
    public CategoryOutputDto updateCategory(@PathVariable Long catId,
                                            @Valid @RequestBody CategoryInputDto categoryInputDto) {
        return adminCategoryService.updateCategory(catId, categoryInputDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long catId) {
        adminCategoryService.deleteCategory(catId);
    }

}
