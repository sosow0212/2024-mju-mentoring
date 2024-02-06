package com.mju.mentoring.member.exception.exceptions;

public class AlreadyRegisteredException extends RuntimeException {

    public AlreadyRegisteredException() {
        super("닉네임이 이미 등록되어 있습니다.");
    }
}
