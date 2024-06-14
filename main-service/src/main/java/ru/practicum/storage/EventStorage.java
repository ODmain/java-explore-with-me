package ru.practicum.storage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.constant.State;
import ru.practicum.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventStorage extends JpaRepository<Event, Long> {

    Page<Event> findAllByInitiatorId(Long initiatorId, Pageable pageable);

    @Query(value = "SELECT e FROM Event e " +
            "WHERE (e.initiator.id IN ?1 OR ?1 IS NULL) " +
            "AND (e.state IN ?2 OR ?2 IS NULL) " +
            "AND (e.category.id IN ?3 OR ?3 IS NULL) " +
            "AND (e.eventDate BETWEEN ?4 AND ?5) " )
    Page<Event> findEventsByOptions(List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd, Pageable pageable);

    @Query(value = "SELECT e FROM Event e " +
            "WHERE (e.state = 'PUBLISHED')" +
            "AND ((LOWER(e.annotation) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(e.description) LIKE LOWER(CONCAT('%', ?1, '%'))) OR ?1 IS NULL) " +
            "AND (e.category.id IN ?2 OR ?2 IS NULL)" +
            "AND (e.paid = ?3 OR ?3 IS NULL)" +
            "AND (e.eventDate BETWEEN ?4 AND ?5) ")
    Page<Event> findAllByOptionsForPublic(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                          LocalDateTime rangeEnd, Pageable pageable);


    List<Event> findAllByIdIn(List<Long> ids);
}
