package com.mju.mentoring.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Point {

    @Column
    private Long point;

    public static Point createDefault() {
        return new Point(0L);
    }

    public static Point from(final Long point) {
        return new Point(point);
    }

    public void increasePoint(final Long plusPoint) {
        point += plusPoint;
    }
}
