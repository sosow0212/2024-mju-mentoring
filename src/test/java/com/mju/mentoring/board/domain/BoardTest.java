package com.mju.mentoring.board.domain;

import static com.mju.mentoring.board.fixture.BoardFixtures.게시글_id_없음;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class BoardTest {

    @Test
    void 글의_제목과_내용을_수정한다() {
        // given
        String editTitle = "title (edited)";
        String editContent = "content (edited)";
        Board originBoard = 게시글_id_없음();

        // when
        originBoard.updateText(editTitle, editContent);

        // then
        assertSoftly((softly) -> {
            softly.assertThat(originBoard.getTitle()).isEqualTo(editTitle);
            softly.assertThat(originBoard.getContent()).isEqualTo(editContent);
        });
    }
}
