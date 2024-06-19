package ru.practicum.service.impl.priv;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.constant.State;
import ru.practicum.constant.Status;
import ru.practicum.dto.request.RequestOutputDto;
import ru.practicum.exception.ValidException;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.Event;
import ru.practicum.model.Request;
import ru.practicum.model.User;
import ru.practicum.service.api.priv.PrivateRequestService;
import ru.practicum.storage.EventStorage;
import ru.practicum.storage.RequestStorage;
import ru.practicum.storage.UserStorage;

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
        User user = validUser(userId);
        if (event.getInitiator().getId().equals(userId)) {
            throw new ValidException("Initiator can not participate in his event", HttpStatus.CONFLICT);
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ValidException("Event was not published", HttpStatus.CONFLICT);
        }
        List<Request> requests = requestStorage.findAllByEventId(eventId);
        for (Request request : requests) {
            if (request.getRequester().getId().equals(userId)) {
                throw new ValidException("You are already participating", HttpStatus.CONFLICT);
            }
        }
        if (event.getParticipantLimit() == 0) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventStorage.save(event);
            return requestMapper.toRequestOutputDto(requestStorage.save(new Request().toBuilder()
                    .created(LocalDateTime.now())
                    .event(event)
                    .requester(user)
                    .status(Status.CONFIRMED)
                    .build()));
        }
        if (!event.getRequestModeration()) {
            Long count = requestStorage.countRequestByEventIdAndStatus(eventId, Status.CONFIRMED);
            if (event.getParticipantLimit() != 0 && count.equals(event.getParticipantLimit())) {
                throw new ValidException("Guest limit exceeded", HttpStatus.CONFLICT);
            }
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventStorage.save(event);
            return requestMapper.toRequestOutputDto(requestStorage.save(new Request().toBuilder()
                    .created(LocalDateTime.now())
                    .event(event)
                    .requester(user)
                    .status(Status.CONFIRMED)
                    .build()));

        }
        Long count = requestStorage.countRequestByEventIdAndStatus(eventId, Status.CONFIRMED);
        if (event.getParticipantLimit() != 0 && count.equals(event.getParticipantLimit())) {
            throw new ValidException("Guest limit exceeded", HttpStatus.CONFLICT);
        }
        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        eventStorage.save(event);
        return requestMapper.toRequestOutputDto(requestStorage.save(new Request().toBuilder()
                .created(LocalDateTime.now())
                .event(event)
                .requester(user)
                .status(Status.PENDING)
                .build()));
    }

    @Override
    @Transactional
    public RequestOutputDto updateRequest(Long userId, Long requestId) {
        validUser(userId);
        Request request = requestStorage.findById(requestId).orElseThrow(() ->
                new ValidException("Request was not found", HttpStatus.NOT_FOUND));
        validUserForOwner(userId, request.getRequester().getId());
        request.setStatus(Status.CANCELED);
        return requestMapper.toRequestOutputDto(requestStorage.save(request));
    }

    @Override
    public List<RequestOutputDto> getRequests(Long userId) {
        validUser(userId);
        return requestMapper.toRequestOutputDtoList(requestStorage.findAllByRequesterId(userId));
    }

    private User validUser(Long userId) {
        return userStorage.findById(userId).orElseThrow(() ->
                new ValidException("User was not found", HttpStatus.NOT_FOUND));
    }

    private void validUserForOwner(Long userId, Long owner) {
        if (!userId.equals(owner)) {
            throw new ValidException("User is not owner", HttpStatus.CONFLICT);
        }
    }
}
