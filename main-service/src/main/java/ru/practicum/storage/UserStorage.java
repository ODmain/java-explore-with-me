package ru.practicum.storage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.User;

import java.util.List;

public interface UserStorage extends JpaRepository<User, Long> {
    Page<User> findAllByIdIn(List<Long> ids, Pageable pageable);

    User findByEmail(String email);
}
