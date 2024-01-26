package com.mju.mentoring.board.exception;

import com.mju.mentoring.board.exception.exceptions.BoardNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BoardExceptionHandler {

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<String> handleBoardNotFoundException(final BoardNotFoundException exception) {
        return getResponseWithStatus(HttpStatus.NOT_FOUND, exception);
    }

    private ResponseEntity<String> getResponseWithStatus(final HttpStatus httpStatus,
        final BoardNotFoundException exception) {
        return ResponseEntity.status(httpStatus)
            .body(exception.getMessage());
    }
}
