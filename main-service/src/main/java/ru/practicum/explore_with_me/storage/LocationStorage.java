package ru.practicum.explore_with_me.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore_with_me.model.Location;

public interface LocationStorage extends JpaRepository<Location, Long> {
}
