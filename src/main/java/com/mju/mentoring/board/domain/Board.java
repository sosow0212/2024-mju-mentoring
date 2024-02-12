package com.mju.mentoring.board.domain;

import com.mju.mentoring.member.domain.Member;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private BoardText boardText;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public Board(final BoardText boardText, final Member member) {
        this.boardText = boardText;
        this.member = member;
    }

    public static Board createWithMember(final BoardText boardText, final Member member) {
        return new Board(boardText, member);
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

    public String getWriter() {
        return member.getNickname();
    }
}
