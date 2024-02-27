package com.mju.mentoring.exam.board.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private BoardDescription boardDescription;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	public Board(final String title, final String content, final Member member, final Long Views) {
		this.boardDescription = new BoardDescription(title, content, Views);
		this.member = member;
	}

	public void update(final String title, final String content) {
		this.boardDescription = new BoardDescription(title, content, this.boardDescription.getViews());
	}

	public void addView() {
		this.boardDescription = new BoardDescription(this.boardDescription.getTitle(),
			this.boardDescription.getContent(), this.boardDescription.getViews() + 1);
	}
}
