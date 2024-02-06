package com.mju.mentoring.board.exception;

public class CustomException extends RuntimeException {
    public CustomException(Long id) {
        super("게시글을 찾을 수 없습니다. - " + id);
    }
}