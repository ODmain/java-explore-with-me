package ru.practicum.explore_with_me.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.dto.user.UserInputDto;
import ru.practicum.explore_with_me.dto.user.UserOutputDto;
import ru.practicum.explore_with_me.service.api.admin.AdminUserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {
    private final AdminUserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutputDto createUser(@RequestBody @Valid UserInputDto userInputDto) {
        return userService.createUser(userInputDto);
    }

    @GetMapping
    public List<UserOutputDto> getUsers(@RequestParam(required = false) List<Long> ids,
                                        @RequestParam(required = false, defaultValue = "0") Integer from,
                                        @RequestParam(required = false, defaultValue = "10") Integer size) {
        return userService.getUsers(ids, from, size);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

}
