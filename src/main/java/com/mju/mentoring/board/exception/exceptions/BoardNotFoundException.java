package com.mju.mentoring.board.exception.exceptions;

public class BoardNotFoundException extends RuntimeException {

    public BoardNotFoundException() {
        super("해당되는 글을 찾을 수 없습니다.");
    }
}
