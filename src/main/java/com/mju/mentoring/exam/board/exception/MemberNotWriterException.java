package com.mju.mentoring.exam.board.exception;

public class MemberNotWriterException extends Throwable {
	public MemberNotWriterException() {
		super("비밀번호가 일치하지 않습니다");
	}
}
