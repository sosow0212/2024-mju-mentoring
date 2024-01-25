package com.mju.mentoring.board.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Description description;

    protected Board() {
    }

    private Board(final Description description) {
        this.description = description;
    }

    public void update(final String title, final String content) {
        this.description = Description.of(title, content);
    }

    public Long getId() {
        return id;
    }

    public Description getDescription() {
        return description;
    }

    public String getTitle() {
        return description.getTitle();
    }

    public String getContent() {
        return description.getContent();
    }
}
