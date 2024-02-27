package com.mju.mentoring.board.fixture;

import static com.mju.mentoring.board.fixture.MemberFixture.*;

import com.mju.mentoring.exam.board.domain.Board;
import com.mju.mentoring.exam.board.domain.BoardDescription;

public class BoardFixture {

	// 정적 팩토리 메서드
	public static Board 게시글_생성() {
		return Board.builder()
			.id(1L)
			.boardDescription(new BoardDescription("title1", "content1", 0L))
			.member(멤버_생성())
			.build();
	}

	public static Board 게시글_생성_id없음() {
		return Board.builder()
			.id(1L)
			.boardDescription(new BoardDescription("title2", "content2", 0L))
			.member(멤버_생성())
			.build();
	}
}
