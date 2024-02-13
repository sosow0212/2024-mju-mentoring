package com.mju.mentoring.board.domain;

import static com.mju.mentoring.board.fixture.BoardFixture.id_없는_게시글_생성;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class BoardTest {

    private static final Long DEFAULT_WRITER_ID = 1L;

    @Test
    public void 게시글_업데이트() {
        // given
        Board board = id_없는_게시글_생성();
        String updateTitle = "업데이트 제목";
        String updateContent = "업데이트 내용";

        // when
        board.update(DEFAULT_WRITER_ID, updateTitle, updateContent);

        // then
        assertSoftly(softly -> {
            softly.assertThat(board.getTitle()).isEqualTo(updateTitle);
            softly.assertThat(board.getContent()).isEqualTo(updateContent);
        });
    }
}
