package com.mju.mentoring.member.exception.exceptions;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super("잘못된 비밀번호입니다.");
    }
}
