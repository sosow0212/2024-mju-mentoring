package com.mju.mentoring.board.controller.dto;

public record BoardUpdateResponse(Long id) {

    public static BoardUpdateResponse from(final Long id) {
        return new BoardUpdateResponse(id);
    }
}
