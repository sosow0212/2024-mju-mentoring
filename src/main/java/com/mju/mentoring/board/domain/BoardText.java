package com.mju.mentoring.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class BoardText {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    public BoardText update(final String title, final String content) {
        return new BoardText(title, content);
    }
}
