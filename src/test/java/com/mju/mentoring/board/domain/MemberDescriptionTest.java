package com.mju.mentoring.board.domain;

import static com.mju.mentoring.board.fixture.MemberFixture.*;
import static org.assertj.core.api.SoftAssertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import com.mju.mentoring.exam.board.domain.Member;
import com.mju.mentoring.exam.board.exception.MemberNotFoundException;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberDescriptionTest {

	@Test
	void 비밀번호를_검증한다() {
		// given
		String rightpassword = "password1";
		Member member = 멤버_생성();

		// when & then
		assertSoftly(softly -> {
			softly.assertThat(member.isValidPassword(rightpassword)).isTrue();
		});
	}

	@Test
	void 잘못된_비밀번호_입력시_예외를_검증한다() {
		// given
		String wrongpassword = "wrongpassword";
		Member member = 멤버_생성();

		// when & then
		assertThrows(MemberNotFoundException.class, () -> {
			member.isValidPassword(wrongpassword);
		});
	}
}
