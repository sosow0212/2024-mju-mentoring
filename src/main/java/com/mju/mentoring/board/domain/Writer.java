package com.mju.mentoring.board.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class Writer {

    private Long writerId;
    private String writerName;

    public static Writer of(final Long writerId, final String writerName) {
        return new Writer(writerId, writerName);
    }

    public boolean verifyEquality(final Long writerId) {
        return this.writerId.equals(writerId);
    }
}
