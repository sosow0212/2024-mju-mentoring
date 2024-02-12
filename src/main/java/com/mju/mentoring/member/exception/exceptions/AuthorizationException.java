package com.mju.mentoring.member.exception.exceptions;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException() {
        super("인증 권한이 없습니다.");
    }
}
