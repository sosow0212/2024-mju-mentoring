package com.mju.mentoring.board.controller.dto;

import com.mju.mentoring.board.domain.Board;

public record BoardSearchResponse(Long id, String title, String content, String nickname) {

    public static BoardSearchResponse from(final Board board) {
        return new BoardSearchResponse(board.getId(), board.getTitle(), board.getContent(), board.getWriter());
    }
}
