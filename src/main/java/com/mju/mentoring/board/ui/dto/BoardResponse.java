package com.mju.mentoring.board.ui.dto;

import com.mju.mentoring.board.domain.Board;

public record BoardResponse(Long boardId, String writer, String title, String content,
                            Integer view) {

    public static BoardResponse from(final Board board) {
        return new BoardResponse(board.getId(), board.getWriterName(), board.getTitle(),
            board.getContent(), board.getViewCount());
    }
}
