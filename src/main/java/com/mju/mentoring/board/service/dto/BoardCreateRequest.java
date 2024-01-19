package com.mju.mentoring.board.service.dto;

public class BoardCreateRequest {

    private String title;
    private String content;

    public BoardCreateRequest() {
        // JSON -> 객체로 바꿀 때 리플렉션을 사용하는데 그 때 필요함
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
