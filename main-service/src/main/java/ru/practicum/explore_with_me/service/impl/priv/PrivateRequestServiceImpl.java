package ru.practicum.explore_with_me.service.impl.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.constant.State;
import ru.practicum.explore_with_me.constant.Status;
import ru.practicum.explore_with_me.dto.request.RequestOutputDto;
import ru.practicum.explore_with_me.exception.ValidException;
import ru.practicum.explore_with_me.mapper.RequestMapper;
import ru.practicum.explore_with_me.model.Event;
import ru.practicum.explore_with_me.model.Request;
import ru.practicum.explore_with_me.service.api.priv.PrivateRequestService;
import ru.practicum.explore_with_me.storage.EventStorage;
import ru.practicum.explore_with_me.storage.RequestStorage;
import ru.practicum.explore_with_me.storage.UserStorage;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrivateRequestServiceImpl implements PrivateRequestService {
    private final RequestStorage requestStorage;
    private final EventStorage eventStorage;
    private final UserStorage userStorage;
    private final RequestMapper requestMapper;

    @Override
    @Transactional
    public RequestOutputDto addRequest(Long userId, Long eventId) {
        Event event = eventStorage.findById(eventId).orElseThrow(() ->
                new ValidException("Event was not found", HttpStatus.NOT_FOUND));
        validUser(userId);
        validUserForOwner(userId, event.getInitiator().getId());
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ValidException("Event was not published", HttpStatus.CONFLICT);
        }
        if (requestStorage.getCountAllRequestsByEventAndStatus(event.getId(), Status.CONFIRMED)
                .equals(event.getParticipantLimit())) {
            throw new ValidException("Guest limit exceeded", HttpStatus.CONFLICT);
        }
        return requestMapper.toRequestOutputDto(requestStorage.save(new Request().toBuilder()
                .created(LocalDateTime.now())
                .event(eventId)
                .requester(userId)
                .status(Status.PENDING)
                .build()));
    }

    @Override
    @Transactional
    public RequestOutputDto updateRequest(Long userId, Long requestId) {
        validUser(userId);
        Request request = requestStorage.findById(requestId).orElseThrow(() ->
                new ValidException("Request was not found", HttpStatus.NOT_FOUND));
        validUserForOwner(userId, request.getRequester());
        request.setStatus(Status.CANCELED);
        return requestMapper.toRequestOutputDto(requestStorage.save(request));
    }

    @Override
    public List<RequestOutputDto> getRequests(Long userId) {
        validUser(userId);
        return requestMapper.toRequestOutputDtoList(requestStorage.findAllByRequester(userId));
    }

    private void validUser(Long userId) {
        if (!userStorage.existsById(userId)) {
            throw new ValidException("User was not found", HttpStatus.NOT_FOUND);
        }
    }

    private void validUserForOwner(Long userId, Long owner) {
        if (!userId.equals(owner)) {
            throw new ValidException("User is not owner", HttpStatus.CONFLICT);
        }
    }
}
