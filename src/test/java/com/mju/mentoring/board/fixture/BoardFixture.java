package com.mju.mentoring.board.fixture;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.domain.Description;
import com.mju.mentoring.board.domain.Writer;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class BoardFixture {

    private static final Long DEFAULT_ID = 1L;

    public static Board id_없는_게시글_생성() {
        return Board.of(1L,"nickname", "테스트 제목", "테스트 내용");
    }

    public static Board 게시글_생성() {
        return Board.builder()
            .id(DEFAULT_ID)
            .writer(Writer.of(1L, "nickname"))
            .description(Description.of("테스트 제목", "테스트 내용"))
            .build();
    }
}
