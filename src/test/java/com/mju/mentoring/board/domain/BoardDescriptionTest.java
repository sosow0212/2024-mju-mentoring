package com.mju.mentoring.board.domain;

import com.mju.mentoring.exam.board.domain.Board;
import com.mju.mentoring.exam.board.domain.Member;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.mju.mentoring.board.fixture.BoardFixture.게시글_생성;
import static com.mju.mentoring.board.fixture.MemberFixture.멤버_생성;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class BoardDescriptionTest {

    @Test
    void 게시글을_업데이트한다() {
        // given
        Member member = 멤버_생성();
        String newTitle = "newTitle";
        String newContent = "newContent";
        Board board = 게시글_생성(member);

        // when
        board.update(newTitle, newContent);

        // then
        assertSoftly(softly -> {
            softly.assertThat(board.getBoardDescription().getTitle()).isEqualTo(newTitle);
            softly.assertThat(board.getBoardDescription().getContent()).isEqualTo(newContent);
        });
    }
}
