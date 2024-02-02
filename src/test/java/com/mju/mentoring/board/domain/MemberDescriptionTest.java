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
class MemberDescriptionTest {

    @Test
    void 비밀번호를_검증한다() {
        // given
        String wrongpassword = "wrongpassword";
        String rightpassword = "password1";
        Member member = 멤버_생성();

        // when & then
        assertSoftly(softly -> {
            softly.assertThat(member.validation(wrongpassword)).isEqualTo(false);
            softly.assertThat(member.validation(rightpassword)).isEqualTo(true);
        });
    }
}
