package ru.practicum.controller.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.CategoryOutputDto;
import ru.practicum.service.api.pub.PublicCategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {
    private final PublicCategoryService categoryService;

    @GetMapping
    public List<CategoryOutputDto> getAllCategories(@RequestParam(required = false, defaultValue = "0") Integer from,
                                                    @RequestParam(required = false, defaultValue = "10") Integer size) {
        return categoryService.getAllCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryOutputDto getCategoryById(@PathVariable Long catId) {
        return categoryService.getCategoryById(catId);
    }
}
