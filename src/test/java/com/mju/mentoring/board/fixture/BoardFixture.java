package com.mju.mentoring.board.fixture;

import com.mju.mentoring.exam.board.domain.Board;
import com.mju.mentoring.exam.board.domain.BoardDescription;
import com.mju.mentoring.exam.board.domain.Member;

public class BoardFixture {
	static int loginId = 1;

	// 정적 팩토리 메서드
	public static Board 게시글_생성(Member member) {
		return Board.builder()
			.id(1L)
			.boardDescription(new BoardDescription("title" + loginId++, "content1"))
			.member(member)
			.build();
	}

	public static Board 게시글_생성_id없음(Member member) {
		return Board.builder()
			.boardDescription(new BoardDescription("title" + loginId++, "content2"))
			.member(member)
			.build();
	}
}
