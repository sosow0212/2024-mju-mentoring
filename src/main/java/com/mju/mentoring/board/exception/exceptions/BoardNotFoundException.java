package com.mju.mentoring.board.exception.exceptions;

public class BoardNotFoundException extends RuntimeException {

    public BoardNotFoundException() {
        super("해당 게시물을 찾을 수 없습니다");
    }
}
