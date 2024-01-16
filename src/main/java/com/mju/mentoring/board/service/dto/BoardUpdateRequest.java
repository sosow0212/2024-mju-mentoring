package com.mju.mentoring.board.service.dto;

public class BoardUpdateRequest {

    private final String title;
    private final String content;

    public BoardUpdateRequest(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
