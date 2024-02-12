package com.mju.mentoring.board.exception.exceptions;

public class NotBoardWriterException extends RuntimeException {

    public NotBoardWriterException() {
        super("게시글의 작성자가 아닙니다");
    }
}
