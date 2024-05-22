package ru.practicum.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@ToString
@Setter
@Builder
public class ValidException extends RuntimeException {
    private String message;
    private HttpStatus status;
}
