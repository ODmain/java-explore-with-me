package ru.practicum.explore_with_me.controller.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.dto.request.RequestOutputDto;
import ru.practicum.explore_with_me.service.api.priv.PrivateRequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class PrivateRequestController {
    private final PrivateRequestService privateRequestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestOutputDto addRequest(@PathVariable Long userId,
                                       @RequestParam Long eventId) {
        return privateRequestService.addRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestOutputDto updateRequest(@PathVariable Long userId,
                                          @PathVariable Long requestId) {
        return privateRequestService.updateRequest(userId, requestId);
    }

    @GetMapping
    public List<RequestOutputDto> getRequests(@PathVariable Long userId) {
        return privateRequestService.getRequests(userId);
    }
}
