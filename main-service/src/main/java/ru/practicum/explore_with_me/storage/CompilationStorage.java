package ru.practicum.explore_with_me.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explore_with_me.model.Compilation;

@Repository
public interface CompilationStorage extends JpaRepository<Compilation, Long> {
}
