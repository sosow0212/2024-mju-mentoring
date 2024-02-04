package com.mju.mentoring.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "알 수 없는 에러가 발생했습니다. 관리자에게 문의해주세요.";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        String errorMessage = Objects.requireNonNull(exception.getBindingResult().getFieldError())
                .getDefaultMessage();

        return createExceptionResponseWithStatusAndMessage(BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(value = {IllegalStateException.class, IllegalArgumentException.class})
    public ResponseEntity<ExceptionResponse> handleGlobalIllegalException(final RuntimeException exception) {
        log.error(exception.getMessage());

        return createExceptionResponseWithStatusAndMessage(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MESSAGE);
    }

    public static ResponseEntity<ExceptionResponse> createExceptionResponseWithStatusAndMessage(final HttpStatus status,
                                                                                          final String message) {
        return ResponseEntity.status(status)
                .body(new ExceptionResponse(message));
    }
}
