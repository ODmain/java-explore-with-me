package ru.practicum.explore_with_me.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.explore_with_me.dto.category.CategoryInputDto;
import ru.practicum.explore_with_me.dto.category.CategoryOutputDto;
import ru.practicum.explore_with_me.model.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryOutputDto toCategoryOutputDto(Category category);

    @Mapping(target = "id", ignore = true)
    Category toCategoryFromInput(CategoryInputDto categoryInputDto);

    List<CategoryOutputDto> toCategoryOutputDtoList(List<Category> categoryList);
}
