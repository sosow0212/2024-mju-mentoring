package com.mju.mentoring.mission.exception.exceptions;

public class NoCompletedProgressException extends RuntimeException {

    public NoCompletedProgressException() {
        super("받을 수 있는 보상이 없습니다!");
    }
}
