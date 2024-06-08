package ru.practicum.explore_with_me.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.constant.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder(toBuilder = true)
@Table(name = "requests")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    LocalDateTime created;
    @Column(nullable = false)
    Long event;
    @Column(nullable = false)
    Long requester;
    @Column(nullable = false)
    Status status;
}
