package com.mju.mentoring.board.controller.dto;

public record BoardSearchResponse(Long id, String title, String content) {

    public static BoardSearchResponse of(final Long id, final String title, final String content) {
        return new BoardSearchResponse(id, title, content);
    }
}
