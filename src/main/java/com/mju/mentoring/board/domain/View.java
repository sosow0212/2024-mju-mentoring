package com.mju.mentoring.board.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class View {

    private Integer view;

    public static View createDefault() {
        return new View(0);
    }

    public void increaseView() {
        view++;
    }
}
