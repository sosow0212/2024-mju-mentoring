package com.mju.mentoring.board.controller.dto;

public record BoardCreateResponse(Long id) {

    public static BoardCreateResponse from(final Long id) {
        return new BoardCreateResponse(id);
    }
}
