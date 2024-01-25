package com.mju.mentoring.board.domain;

import jakarta.persistence.Embedded;
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

    @Embedded
    private BoardText boardText;

    public Board(final String title, final String content) {
        this.boardText = new BoardText(title, content);
    }

    public void updateText(final String title, final String content) {
        this.boardText = boardText.update(title, content);
    }

    public String getTitle() {
        return boardText.getTitle();
    }

    public String getContent() {
        return boardText.getContent();
    }
}
