package com.mju.mentoring.board.fixture;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.domain.Description;

public class BoardFixture {

    // 정적 팩토리 메서드
    public static Board 게시글_생성() {
        return Board.builder()
                .id(1L)
                .description(new Description("title", "content"))
                .build();
    }

    public static Board 게시글_생성_id없음() {
        return Board.builder()
                .id(1L)
                .description(new Description("title", "content"))
                .build();
    }
}
