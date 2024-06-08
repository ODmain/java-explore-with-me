package ru.practicum.explore_with_me.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder(toBuilder = true)
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String email;
    @Column(nullable = false)
    String name;
}
