package ru.practicum.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseError {
    private String error;
}