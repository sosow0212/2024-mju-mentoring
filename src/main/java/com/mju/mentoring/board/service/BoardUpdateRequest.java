package com.mju.mentoring.board.service;

public class BoardUpdateRequest {

    private String title;
    private String content;

    public BoardUpdateRequest() {
    }

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
