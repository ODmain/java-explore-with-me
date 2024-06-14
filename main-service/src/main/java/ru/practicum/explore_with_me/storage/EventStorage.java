package ru.practicum.explore_with_me.storage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore_with_me.constant.State;
import ru.practicum.explore_with_me.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventStorage extends JpaRepository<Event, Long> {

    Page<Event> findAllByInitiatorId(Long initiatorId, Pageable pageable);

    @Query(value = "SELECT e FROM Event e " +
            "WHERE (:categories IS NULL OR e.category.id IN :categories) " +
            "AND (:states IS NULL OR e.state IN :states) " +
            "AND (:users IS NULL OR e.initiator.id IN :users) " +
            "AND (e.eventDate >= :rangeStart) " +
            "AND (e.eventDate < :rangeEnd)")
    Page<Event> findEventsByOptions(List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd, Pageable pageable);

    List<Event> findAllByIdIn(List<Long> ids);
}
