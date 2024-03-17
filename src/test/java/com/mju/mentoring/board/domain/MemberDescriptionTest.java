package com.mju.mentoring.board.domain;

import static com.mju.mentoring.board.fixture.MemberFixture.멤버_생성;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import com.mju.mentoring.exam.board.domain.Member;
import com.mju.mentoring.exam.board.exception.BadCredentialsException;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberDescriptionTest {

	@Test
	void 비밀번호를_검증한다() {
		// given
		String rightpassword = "password1";
		Member member = 멤버_생성();

		// when & then
		assertDoesNotThrow(() -> member.isValidPassword(rightpassword));
	}

	@Test
	void 잘못된_비밀번호_입력시_예외를_검증한다() {
		// given
		String wrongpassword = "wrongpassword";
		Member member = 멤버_생성();

		// when & then
		assertThrows(BadCredentialsException.class, () -> {
			member.isValidPassword(wrongpassword);
		});
	}
}
