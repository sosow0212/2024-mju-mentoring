package com.mju.mentoring.exam.board.exception;

public class BadCredentialsException extends Throwable {
	public BadCredentialsException() {
		super("비밀번호가 일치하지 않습니다");
	}
}
