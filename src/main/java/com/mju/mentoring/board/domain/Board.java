package com.mju.mentoring.board.domain;

import com.mju.mentoring.board.exception.exceptions.NotBoardWriterException;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@EqualsAndHashCode(of = "id")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Description description;

    @Embedded
    private Writer writer;

    private Board(final Description description, final Writer writer) {
        this.description = description;
        this.writer = writer;
    }

    public static Board of(final Long writerId, final String writerName, final String title,
        final String content) {
        return new Board(Description.of(title, content), Writer.of(writerId, writerName));
    }

    public void update(final Long writerId, final String title, final String content) {
        verifyWriter(writerId);
        this.description = Description.of(title, content);
    }

    public void verifyWriter(final Long writerId) {
        if (!writer.verifyEquality(writerId)) {
            throw new NotBoardWriterException();
        }
    }

    public Description copyDescription() {
        return description.copy();
    }

    public String getTitle() {
        return description.getTitle();
    }

    public String getContent() {
        return description.getContent();
    }

    public Long getWriterId() {
        return writer.getWriterId();
    }

    public String getWriterName() {
        return writer.getWriterName();
    }
}
