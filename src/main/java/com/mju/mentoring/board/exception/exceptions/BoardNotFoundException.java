package com.mju.mentoring.board.exception.exceptions;

public class BoardNotFoundException extends RuntimeException {

    public BoardNotFoundException() {
        super("게시글이 존재하지 않습니다.");
    }
}
