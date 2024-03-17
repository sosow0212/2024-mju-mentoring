package com.mju.mentoring.exam.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class BoardDescription {

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private Long views;

	public BoardDescription(final String title, final String content) {
		this.title = title;
		this.content = content;
		this.views = 0L;
	}

	public void update(final String title, final String content) {
		this.title = title;
		this.content = content;
	}

	public void addView() {
		this.views++;
	}
}
