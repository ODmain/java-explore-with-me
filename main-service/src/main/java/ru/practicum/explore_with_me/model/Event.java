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
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    Category category;
    @Column(name = "confirmed_requests", nullable = false)
    Long confirmedRequests;
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
    @Column(name = "is_paid", nullable = false)
    Boolean paid;
    @Column(name = "participant_limit", nullable = false)
    Long participantLimit;
    @Column(name = "published_on", nullable = false)
    LocalDateTime publishedOn;
    @Column(name = "request_moderation", nullable = false)
    Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    State state;
    @Column(nullable = false)
    String title;
    @Column(nullable = false)
    Long views;
}
