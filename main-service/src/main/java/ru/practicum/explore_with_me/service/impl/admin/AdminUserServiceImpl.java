package ru.practicum.explore_with_me.service.impl.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.dto.user.UserInputDto;
import ru.practicum.explore_with_me.dto.user.UserOutputDto;
import ru.practicum.explore_with_me.exception.ValidException;
import ru.practicum.explore_with_me.mapper.UserMapper;
import ru.practicum.explore_with_me.service.api.admin.AdminUserService;
import ru.practicum.explore_with_me.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserServiceImpl implements AdminUserService {
    private final UserStorage userStorage;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserOutputDto createUser(UserInputDto userInputDto) {
        return userMapper.toUserOutputDto(userStorage.save(userMapper.toUserFromInputDto(userInputDto)));
    }

    @Override
    public List<UserOutputDto> getUsers(List<Long> ids, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by("id").descending());
        if (ids.isEmpty()) {
            return userStorage.findAllByIdIn(ids, pageable).getContent().stream()
                    .map(userMapper::toUserOutputDto)
                    .collect(Collectors.toList());
        } else {
            return userStorage.findAll(pageable).getContent().stream()
                    .map(userMapper::toUserOutputDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        if (!userStorage.existsById(userId)) {
            throw new ValidException("User was not found", HttpStatus.NOT_FOUND);
        }
        userStorage.deleteById(userId);
    }
}
