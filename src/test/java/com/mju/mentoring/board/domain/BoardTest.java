package com.mju.mentoring.board.domain;

import static com.mju.mentoring.board.fixture.BoardFixture.id_없는_게시글_생성;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class BoardTest {

    @Test
    public void 게시글_업데이트_값_객체_인스턴스_교체() {
        // given
        Board board = id_없는_게시글_생성();
        Description origin = board.getDescription();

        // when
        board.update("업데이트 제목", "업데이트 내용");

        // then
        assertNotEquals(origin, board.getDescription());
    }
}
