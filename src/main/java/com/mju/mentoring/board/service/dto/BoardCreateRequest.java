package com.mju.mentoring.board.service.dto;

public class BoardCreateRequest {

    private String title;
    private String content;

    public BoardCreateRequest() {
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
