package com.mju.mentoring.board.ui.dto;

import com.mju.mentoring.board.domain.Board;

public record BoardResponse(Long boardId, String title, String content) {

    public static BoardResponse from(final Board board) {
        return new BoardResponse(board.getId(), board.getTitle(), board.getContent());
    }
}
