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
@Table(name = "locations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Location {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    @Column(nullable = false)
    Double lat;
    @Column(nullable = false)
    Double lon;
}
