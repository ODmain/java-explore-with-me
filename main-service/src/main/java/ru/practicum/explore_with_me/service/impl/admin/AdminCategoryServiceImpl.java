package ru.practicum.explore_with_me.service.impl.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.dto.category.CategoryInputDto;
import ru.practicum.explore_with_me.dto.category.CategoryOutputDto;
import ru.practicum.explore_with_me.exception.ValidException;
import ru.practicum.explore_with_me.mapper.CategoryMapper;
import ru.practicum.explore_with_me.model.Category;
import ru.practicum.explore_with_me.service.api.admin.AdminCategoryService;
import ru.practicum.explore_with_me.storage.CategoryStorage;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryStorage categoryStorage;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryOutputDto addCategory(CategoryInputDto categoryInputDto) {
        return categoryMapper.toCategoryOutputDto(categoryStorage.save(categoryMapper
                .toCategoryFromInput(categoryInputDto)));
    }

    @Override
    public CategoryOutputDto updateCategory(Long catId, CategoryInputDto categoryInputDto) {
        Category category = categoryStorage.findById(catId).orElseThrow(()
                -> new ValidException("Category was not found", HttpStatus.NOT_FOUND));
        category.setName(categoryInputDto.getName());
        return categoryMapper.toCategoryOutputDto(categoryStorage.save(category));
    }

    @Override
    public void deleteCategory(Long catId) {
        if (!categoryStorage.existsById(catId)) {
            throw new ValidException("Category was not found", HttpStatus.NOT_FOUND);
        }
        categoryStorage.deleteById(catId);
    }
}
