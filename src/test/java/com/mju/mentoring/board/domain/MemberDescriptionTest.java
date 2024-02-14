package com.mju.mentoring.board.domain;

import static com.mju.mentoring.board.fixture.MemberFixture.*;
import static org.assertj.core.api.SoftAssertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import com.mju.mentoring.exam.board.domain.Member;

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
			softly.assertThat(member.isValidPassword(wrongpassword)).isFalse();
			softly.assertThat(member.isValidPassword(rightpassword)).isTrue();
		});
	}
}
