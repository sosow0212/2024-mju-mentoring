package com.mju.mentoring.mission.exception.exceptions;

public class InvalidChallengerException extends RuntimeException {

    public InvalidChallengerException() {
        super("유효한 도전자가 아닙니다!");
    }
}
