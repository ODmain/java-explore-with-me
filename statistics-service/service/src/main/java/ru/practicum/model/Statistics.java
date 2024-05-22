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
    @Column(nullable = false, length = 200)
    private String app;
    @Column(nullable = false, length = 200)
    private String uri;
    @Column(nullable = false, length = 200)
    private String ip;
    @Column(nullable = false)
    private LocalDateTime timestamp;
}
