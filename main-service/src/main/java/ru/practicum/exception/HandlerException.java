package ru.practicum.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerException {
    @ExceptionHandler(ValidException.class)
    public ResponseEntity<ResponseError> notFound(ValidException e) {
        return new ResponseEntity<>(ResponseError.builder()
                .error(e.getMessage())
                .build(), e.getStatus());
    }
}