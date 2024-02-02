package com.mju.mentoring.member.exception;

public class CookieException extends RuntimeException {

    public CookieException() {
        super("쿠키 값이 올바르지 않습니다.");
    }
}
