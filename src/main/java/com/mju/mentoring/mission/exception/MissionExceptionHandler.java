package com.mju.mentoring.mission.exception;

import com.mju.mentoring.mission.exception.exceptions.InvalidChallengerException;
import com.mju.mentoring.mission.exception.exceptions.NoCompletedProgressException;
import com.mju.mentoring.mission.exception.exceptions.NotFoundMissionException;
import com.mju.mentoring.mission.exception.exceptions.WrongProgressStatusException;
import com.mju.mentoring.mission.exception.exceptions.WrongRewardStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MissionExceptionHandler {

    @ExceptionHandler(NotFoundMissionException.class)
    public ResponseEntity<String> handleNotFoundMissionException(
        final NotFoundMissionException exception) {
        return getResponseWithStatus(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(WrongProgressStatusException.class)
    public ResponseEntity<String> handleWrongProgressStatusException(
        final WrongProgressStatusException exception
    ) {
        return getResponseWithStatus(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(WrongRewardStatusException.class)
    public ResponseEntity<String> handleWrongRewardStatusException(
        final WrongRewardStatusException exception
    ) {
        return getResponseWithStatus(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleNoCompletedProgress(
        final NoCompletedProgressException exception
    ) {
        return getResponseWithStatus(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleInvalidChallengerException(
        final InvalidChallengerException exception
    ) {
        return getResponseWithStatus(HttpStatus.BAD_REQUEST, exception);
    }

    private ResponseEntity<String> getResponseWithStatus(final HttpStatus httpStatus,
        final Exception exception) {
        return ResponseEntity.status(httpStatus)
            .body(exception.getMessage());
    }
}
