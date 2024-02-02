package com.mju.mentoring.member.exception;

public class EncodedException extends RuntimeException {

    public EncodedException() {
        super("비밀번호 인코딩 과정에서 예외가 발생했습니다.");
    }
}
