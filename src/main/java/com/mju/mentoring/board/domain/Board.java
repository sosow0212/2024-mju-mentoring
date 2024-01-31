package com.mju.mentoring.board.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
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

    public static Board of(final String title, final String content) {
        return new Board(Description.of(title, content));
    }

    public void update(final String title, final String content) {
        this.description = Description.of(title, content);
    }

    public Description copyDescription() {
        return description.copy();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return description.getTitle();
    }

    public String getContent() {
        return description.getContent();
    }
}
