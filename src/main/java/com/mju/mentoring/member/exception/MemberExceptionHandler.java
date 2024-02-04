package com.mju.mentoring.member.exception;

import static com.mju.mentoring.global.exception.GlobalExceptionHandler.createExceptionResponseWithStatusAndMessage;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.mju.mentoring.global.exception.ExceptionResponse;
import com.mju.mentoring.member.exception.exceptions.CookieException;
import com.mju.mentoring.member.exception.exceptions.MemberNotFoundException;
import com.mju.mentoring.member.exception.exceptions.PasswordNotMatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ExceptionResponse> memberNotFoundExceptionHandler(final MemberNotFoundException exception) {
        return createExceptionResponseWithStatusAndMessage(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(value = {PasswordNotMatchException.class, CookieException.class})
    public ResponseEntity<ExceptionResponse> memberBadRequestExceptionHandler(final RuntimeException exception) {
        return createExceptionResponseWithStatusAndMessage(BAD_REQUEST, exception.getMessage());
    }
}
