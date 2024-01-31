package com.mju.mentoring.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import java.util.Objects;

@Embeddable
public class Description {

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    protected Description() {

    }

    private Description(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public static Description of(final String title, final String content) {
        return new Description(title, content);
    }

    public Description copy() {
        return new Description(title, content);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Description that = (Description) object;
        return Objects.equals(title, that.title) && Objects.equals(content,
            that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content);
    }
}
