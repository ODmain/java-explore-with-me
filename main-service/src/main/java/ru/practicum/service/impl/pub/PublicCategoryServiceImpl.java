package ru.practicum.service.impl.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.category.CategoryOutputDto;
import ru.practicum.exception.ValidException;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.model.Category;
import ru.practicum.service.api.pub.PublicCategoryService;
import ru.practicum.storage.CategoryStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicCategoryServiceImpl implements PublicCategoryService {
    private final CategoryStorage categoryStorage;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryOutputDto> getAllCategories(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<Category> categories = categoryStorage.findAll(pageable).getContent();
        return categoryMapper.toCategoryOutputDtoList(categories);
    }

    @Override
    public CategoryOutputDto getCategoryById(Long id) {
        Category category = categoryStorage.findById(id).orElseThrow(() ->
                new ValidException("Category was not found", HttpStatus.NOT_FOUND));
        return categoryMapper.toCategoryOutputDto(category);
    }
}
