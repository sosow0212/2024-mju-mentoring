package com.mju.mentoring.board.domain;

import com.mju.mentoring.board.exception.exceptions.NotBoardWriterException;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
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
@Table(indexes = @Index(name = "board_title_index", columnList = "title"))
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Description description;

    @Embedded
    private Writer writer;

    @Embedded
    private View view;

    private Board(final Description description, final Writer writer, final View view) {
        this.description = description;
        this.writer = writer;
        this.view = view;
    }

    public static Board of(final Long writerId, final String writerName, final String title,
        final String content) {
        return new Board(Description.of(title, content), Writer.of(writerId, writerName),
            View.createDefault());
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

    public void changeWriter(final Long writerId, final String newWriterName) {
        this.writer = Writer.of(writerId, newWriterName);
    }

    public Description copyDescription() {
        return description.copy();
    }

    public void viewBoard() {
        view.increaseView();
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

    public Integer getViewCount() {
        return view.getView();
    }
}
