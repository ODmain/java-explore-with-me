package ru.practicum.explore_with_me.service.api.admin;

import ru.practicum.explore_with_me.dto.user.UserInputDto;
import ru.practicum.explore_with_me.dto.user.UserOutputDto;

import java.util.List;

public interface AdminUserService {
    UserOutputDto createUser(UserInputDto userInputDto);

    List<UserOutputDto> getUsers(List<Long> ids, Integer from, Integer size);

    void deleteUser(Long userId);
}