package ru.practicum.explore_with_me.storage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explore_with_me.model.Event;

@Repository
public interface EventStorage extends JpaRepository<Event, Long> {

    Page<Event> findAllByInitiatorId(Long initiatorId, Pageable pageable);
}
