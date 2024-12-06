package com.mju.mentoring.mission.exception.exceptions;

public class NotFoundProgressException extends RuntimeException {

    public NotFoundProgressException(final Long id) {
        super("id가 " + id + "인 진행도를 찾을 수 없습니다.");
    }
}
