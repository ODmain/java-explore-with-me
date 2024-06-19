package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.dto.category.CategoryInputDto;
import ru.practicum.dto.category.CategoryOutputDto;
import ru.practicum.model.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryOutputDto toCategoryOutputDto(Category category);

    @Mapping(target = "id", ignore = true)
    Category toCategoryFromInput(CategoryInputDto categoryInputDto);

    List<CategoryOutputDto> toCategoryOutputDtoList(List<Category> categoryList);
}
