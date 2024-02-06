package com.mju.mentoring.member.exception.exceptions;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super("해당 닉네임을 가진 멤버가 없습니다.");
    }
}
