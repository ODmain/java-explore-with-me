package ru.practicum.service.impl.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.user.UserInputDto;
import ru.practicum.dto.user.UserOutputDto;
import ru.practicum.exception.ValidException;
import ru.practicum.mapper.UserMapper;
import ru.practicum.model.User;
import ru.practicum.service.api.admin.AdminUserService;
import ru.practicum.storage.UserStorage;

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
        User user = userStorage.findByEmail(userInputDto.getEmail());
        if (user != null) {
            throw new ValidException("This email is busy", HttpStatus.CONFLICT);
        }
        return userMapper.toUserOutputDto(userStorage.save(userMapper.toUserFromInputDto(userInputDto)));
    }

    @Override
    public List<UserOutputDto> getUsers(List<Long> ids, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        if (ids != null && !ids.isEmpty()) {
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
