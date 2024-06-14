package ru.practicum.service.api.admin;

import ru.practicum.dto.category.CategoryInputDto;
import ru.practicum.dto.category.CategoryOutputDto;

public interface AdminCategoryService {
    CategoryOutputDto addCategory(CategoryInputDto categoryInputDto);

    CategoryOutputDto updateCategory(Long catId, CategoryInputDto categoryInputDto);

    void deleteCategory(Long catId);

}
