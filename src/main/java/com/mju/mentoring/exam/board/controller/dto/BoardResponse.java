package com.mju.mentoring.exam.board.controller.dto;

import com.mju.mentoring.exam.board.domain.Board;

public record BoardResponse(Long id, String title, String content) {

	public static BoardResponse from(Board board) {
		return new BoardResponse(
			board.getId(),
			board.getBoardDescription().getTitle(),
			board.getBoardDescription().getContent()
		);
	}
}
