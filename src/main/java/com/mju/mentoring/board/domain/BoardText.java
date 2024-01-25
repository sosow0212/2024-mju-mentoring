package com.mju.mentoring.board.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class BoardText {

    private String title;
    private String content;

    public BoardText update(final String title, final String content) {
        return new BoardText(title, content);
    }
}
