package ru.practicum.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Location;

public interface LocationStorage extends JpaRepository<Location, Long> {
}
