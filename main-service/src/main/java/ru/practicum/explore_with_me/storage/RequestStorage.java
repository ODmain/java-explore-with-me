package ru.practicum.explore_with_me.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore_with_me.constant.Status;
import ru.practicum.explore_with_me.model.Request;

import java.util.List;


public interface RequestStorage extends JpaRepository<Request, Long> {
    List<Request> findAllByRequesterId(Long requester);

    List<Request> findAllByEventId(Long event);

    Long countAllByEventIdAndStatus(Long eventId, Status status);

    List<Request> findAllByEventIdAndStatus(Long eventId, Status status);

    List<Request> findAllByIdInAndEventId(List<Long> ids, Long eventId);
}
