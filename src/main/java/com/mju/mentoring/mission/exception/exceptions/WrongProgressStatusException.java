package com.mju.mentoring.mission.exception.exceptions;

public class WrongProgressStatusException extends RuntimeException {

    public WrongProgressStatusException() {
        super("잘못된 progress status입니다!");
    }
}
