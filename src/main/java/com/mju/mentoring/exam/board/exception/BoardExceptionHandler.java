package com.mju.mentoring.exam.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BoardExceptionHandler {

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<String> handlerBoardNotFoundException(final BoardNotFoundException exception) {
        return getResponseWithStatus(HttpStatus.NOT_FOUND, exception);
    }

    private static ResponseEntity<String> getResponseWithStatus(final HttpStatus status, final BoardNotFoundException exception) {
        return ResponseEntity.status(status)
                .body(exception.getMessage());
    }
}
