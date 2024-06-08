package ru.practicum.explore_with_me.service.api.pub;

import ru.practicum.explore_with_me.dto.category.CategoryOutputDto;

import java.util.List;

public interface PublicCategoryService {
    List<CategoryOutputDto> getAllCategories(Integer from, Integer size);

    CategoryOutputDto getCategoryById(Long id);
}
