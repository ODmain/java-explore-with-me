package ru.practicum.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Category;

public interface CategoryStorage extends JpaRepository<Category, Long> {

    Category findByName(String name);
}
