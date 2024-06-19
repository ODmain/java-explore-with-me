package ru.practicum.service.api.admin;

import ru.practicum.dto.user.UserInputDto;
import ru.practicum.dto.user.UserOutputDto;

import java.util.List;

public interface AdminUserService {
    UserOutputDto createUser(UserInputDto userInputDto);

    List<UserOutputDto> getUsers(List<Long> ids, Integer from, Integer size);

    void deleteUser(Long userId);
}