package com.mju.mentoring.member.exception.exceptions;

public class JwtSignatureException extends RuntimeException {

    public JwtSignatureException() {
        super("JWT 처리 과정에서 예외가 발생했습니다.");
    }
}
