package com.mju.mentoring.member.exception.exceptions;

public class ExpiredTokenException extends RuntimeException {

    public ExpiredTokenException() {
        super("토큰이 만료되었습니다.");
    }
}
