package ru.practicum.explore_with_me.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explore_with_me.dto.user.UserInputDto;
import ru.practicum.explore_with_me.dto.user.UserOutputDto;
import ru.practicum.explore_with_me.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserOutputDto toUserOutputDto(User user);

    User toUserFromInputDto(UserInputDto userInputDto);
}
