package com.mju.mentoring.board.controller.dto;

public class BoardResponse {

    private final Long boardId;
    private final String title;
    private final String content;

    public BoardResponse(final Long boardId, final String title, final String content) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
    }

    public Long getBoardId() {
        return boardId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
