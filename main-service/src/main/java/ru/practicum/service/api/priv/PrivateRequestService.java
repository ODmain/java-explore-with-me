package ru.practicum.service.api.priv;

import ru.practicum.dto.request.RequestOutputDto;

import java.util.List;

public interface PrivateRequestService {
    RequestOutputDto addRequest(Long userId, Long eventId);

    RequestOutputDto updateRequest(Long userId, Long requestId);

    List<RequestOutputDto> getRequests(Long userId);
}
