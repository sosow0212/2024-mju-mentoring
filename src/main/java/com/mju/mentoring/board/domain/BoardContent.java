package com.mju.mentoring.board.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class BoardContent {

    private String title;
    private String content;

    protected BoardContent() {
    }

    public BoardContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    /*public void setTitle(String title) {
        this.title = title;
    }*/

    public String getContent() {
        return content;
    }

    /*public void setContent(String content) {
        this.content = content;
    }*/

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
