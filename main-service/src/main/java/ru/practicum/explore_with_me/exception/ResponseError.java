package ru.practicum.explore_with_me.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseError {
    private String error;
}