package com.mju.mentoring.board.fixture;


import com.mju.mentoring.exam.board.domain.Board;
import com.mju.mentoring.exam.board.domain.BoardDescription;

public class BoardFixture {

    // 정적 팩토리 메서드
    public static Board 게시글_생성() {
        return Board.builder()
                .id(1L)
                .boardDescription(new BoardDescription("title1", "content1"))
                .build();
    }

    public static Board 게시글_생성_id없음() {
        return Board.builder()
                .id(1L)
                .boardDescription(new BoardDescription("title2", "content2"))
                .build();
    }
}
