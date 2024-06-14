package ru.practicum.service.impl.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.category.CategoryInputDto;
import ru.practicum.dto.category.CategoryOutputDto;
import ru.practicum.exception.ValidException;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.service.api.admin.AdminCategoryService;
import ru.practicum.storage.CategoryStorage;
import ru.practicum.storage.EventStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryStorage categoryStorage;
    private final CategoryMapper categoryMapper;
    private final EventStorage eventStorage;

    @Override
    @Transactional
    public CategoryOutputDto addCategory(CategoryInputDto categoryInputDto) {
        checkForBusyName(categoryInputDto.getName(), null);
        return categoryMapper.toCategoryOutputDto(categoryStorage.save(categoryMapper
                .toCategoryFromInput(categoryInputDto)));
    }

    @Override
    @Transactional
    public CategoryOutputDto updateCategory(Long catId, CategoryInputDto categoryInputDto) {
        Category category = categoryStorage.findById(catId).orElseThrow(()
                -> new ValidException("Category was not found", HttpStatus.NOT_FOUND));
        checkForBusyName(categoryInputDto.getName(), catId);
        category.setName(categoryInputDto.getName());
        return categoryMapper.toCategoryOutputDto(categoryStorage.save(category));
    }

    @Override
    @Transactional
    public void deleteCategory(Long catId) {
        if (!categoryStorage.existsById(catId)) {
            throw new ValidException("Category was not found", HttpStatus.NOT_FOUND);
        }
        List<Event> eventList = eventStorage.findAll();
        for (Event event : eventList) {
            if (event.getCategory().getId().equals(catId)) {
                throw new ValidException("Category already in use", HttpStatus.CONFLICT);
            }
        }
        categoryStorage.deleteById(catId);
    }

    private void checkForBusyName(String name, Long catId) {
        Category category = categoryStorage.findByName(name);
        if (category != null) {
            if (catId != null) {
                if (category.getName().equals(name) && !catId.equals(category.getId())) {
                    throw new ValidException("Category's name is already busy", HttpStatus.CONFLICT);
                }
            } else if (category.getName().equals(name)) {
                throw new ValidException("Category's name is already busy", HttpStatus.CONFLICT);
            }
        }
    }
}
