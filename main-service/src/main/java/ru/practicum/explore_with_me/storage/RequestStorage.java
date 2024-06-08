package ru.practicum.explore_with_me.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explore_with_me.constant.Status;
import ru.practicum.explore_with_me.model.Request;

import java.util.List;

@Repository
public interface RequestStorage extends JpaRepository<Request, Long> {
    List<Request> findAllByRequester(Long requester);

    Long getCountAllRequestsByEventAndStatus(Long eventId, Status state);
}
