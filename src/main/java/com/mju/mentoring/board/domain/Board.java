package com.mju.mentoring.board.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Board(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public static Board of(final String title, final String content) {
        return new Board(title, content);
    }

    public void updateTitle(final String title) {
        this.title = title;
    }

    public void updateContent(final String content) {
        this.content = content;
    }
}
