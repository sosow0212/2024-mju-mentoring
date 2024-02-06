package com.mju.mentoring.board.exception;

import static com.mju.mentoring.global.exception.GlobalExceptionHandler.createExceptionResponseWithStatusAndMessage;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.mju.mentoring.board.exception.exceptions.BoardNotFoundException;
import com.mju.mentoring.global.exception.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BoardExceptionHandler {

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<ExceptionResponse> boardNotFoundExceptionHandler(final BoardNotFoundException exception) {
        return createExceptionResponseWithStatusAndMessage(NOT_FOUND, exception.getMessage());
    }
}
