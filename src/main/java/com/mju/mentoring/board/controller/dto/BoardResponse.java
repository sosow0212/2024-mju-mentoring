package com.mju.mentoring.board.controller.dto;

import com.mju.mentoring.board.domain.Board;

public class BoardResponse {

    private final Long id;
    private final String title;
    private final String content;

    private BoardResponse(final Long id, final String title, final String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static BoardResponse from(final Board board) {
        return new BoardResponse(
                board.getId(),
                board.getDescription().getTitle(),
                board.getDescription().getContent()
        );
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
