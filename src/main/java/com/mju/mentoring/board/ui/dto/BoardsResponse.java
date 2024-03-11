package com.mju.mentoring.board.ui.dto;

import com.mju.mentoring.board.domain.Board;
import java.util.List;

public record BoardsResponse(List<BoardResponse> data, CursorInfo cursor) {

    public static BoardsResponse from(final List<Board> data, final int size) {
        List<BoardResponse> boardResponses = data.stream()
            .map(BoardResponse::from)
            .toList();
        return new BoardsResponse(boardResponses, CursorInfo.from(data, size));
    }
}
