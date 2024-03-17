package com.mju.mentoring.exam.board.domain;

import java.nio.file.AccessDeniedException;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "Board", indexes = {@Index(name = "idx_views", columnList = "views")})
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

	public Board(final String title, final String content, final Member member) {
		this.boardDescription = new BoardDescription(title, content);
		this.member = member;
	}

	public void update(final String title, final String content) {
		this.boardDescription.update(title, content);
	}

	public void addView() {
		this.boardDescription.addView();
	}

	public void writerValidation(Member member) throws AccessDeniedException {
		member.writerValidation(member);
	}
}
