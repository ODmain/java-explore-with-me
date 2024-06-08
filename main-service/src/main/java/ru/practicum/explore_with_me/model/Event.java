package ru.practicum.explore_with_me.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.constant.State;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    Category category;
    @Column(nullable = false)
    LocalDateTime createdOn;
    @Column(nullable = false)
    String description;
    @Column(nullable = false)
    LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    User initiator;
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    Location location;
    @Column(nullable = false)
    Boolean paid;
    @Column(nullable = false)
    Long participantLimit;
    @Column(nullable = false)
    LocalDateTime publishedOn;
    @Column(nullable = false)
    Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    State state;
    @Column(nullable = false)
    String title;
    @Column(nullable = false)
    Long views;
}