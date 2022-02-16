package ru.job4j.passportapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<List<Map<String, String>>> handleMethodArgumentNotValidExceptionException(MethodArgumentNotValidException e) {
        log.error("Invalid request {}", e.getMessage(), e);
        return ResponseEntity.badRequest().body(
                e.getFieldErrors().stream()
                        .map(field -> Map.of(
                                field.getField(),
                                String.format("%s. Actual value: %s", field.getDefaultMessage(), field.getRejectedValue())
                        ))
                        .collect(Collectors.toList())
        );
    }
}
