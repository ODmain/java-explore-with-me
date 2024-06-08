package ru.practicum.model;

import lombok.*;

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
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String app;
    @Column(nullable = false, length = 100)
    private String uri;
    @Column(nullable = false, length = 20)
    private String ip;
    @Column(nullable = false)
    private LocalDateTime timestamp;
}
