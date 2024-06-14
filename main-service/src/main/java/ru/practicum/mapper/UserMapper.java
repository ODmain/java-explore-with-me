package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.user.UserInputDto;
import ru.practicum.dto.user.UserOutputDto;
import ru.practicum.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserOutputDto toUserOutputDto(User user);

    User toUserFromInputDto(UserInputDto userInputDto);
}
