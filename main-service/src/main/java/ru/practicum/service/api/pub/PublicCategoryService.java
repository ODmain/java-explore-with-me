package ru.practicum.service.api.pub;

import ru.practicum.dto.category.CategoryOutputDto;

import java.util.List;

public interface PublicCategoryService {
    List<CategoryOutputDto> getAllCategories(Integer from, Integer size);

    CategoryOutputDto getCategoryById(Long id);
}
