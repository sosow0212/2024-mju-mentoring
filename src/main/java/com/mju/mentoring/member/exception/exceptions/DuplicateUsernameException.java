package com.mju.mentoring.member.exception.exceptions;

public class DuplicateUsernameException extends RuntimeException {

    public DuplicateUsernameException() {
        super("이미 사용중인 아이디입니다.");
    }
}
