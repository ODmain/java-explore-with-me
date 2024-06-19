package ru.practicum.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder(toBuilder = true)
@Table(name = "statistics")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, length = 100)
    String app;
    @Column(nullable = false, length = 100)
    String uri;
    @Column(nullable = false, length = 20)
    String ip;
    @Column(nullable = false)
    LocalDateTime timestamp;
}
