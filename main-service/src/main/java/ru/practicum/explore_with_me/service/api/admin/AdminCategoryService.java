package ru.practicum.explore_with_me.service.api.admin;

import ru.practicum.explore_with_me.dto.category.CategoryInputDto;
import ru.practicum.explore_with_me.dto.category.CategoryOutputDto;

public interface AdminCategoryService {
    CategoryOutputDto addCategory(CategoryInputDto categoryInputDto);

    CategoryOutputDto updateCategory(Long catId, CategoryInputDto categoryInputDto);

    void deleteCategory(Long catId);

}
