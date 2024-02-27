package com.mju.mentoring.board.service;

import static com.mju.mentoring.board.fixture.MemberFixture.*;
import static com.mju.mentoring.exam.board.component.JwtTokenProvider.*;
import static org.assertj.core.api.SoftAssertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import com.mju.mentoring.board.infrastructure.MemberFakeRepository;
import com.mju.mentoring.exam.board.component.JwtTokenProvider;
import com.mju.mentoring.exam.board.domain.Member;
import com.mju.mentoring.exam.board.domain.MemberRepository;
import com.mju.mentoring.exam.board.service.MemberService;
import com.mju.mentoring.exam.board.service.dto.LoginRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberServiceTest {

	// 1. stub 가짜 객체를 사용해서 단위테스트 수행 (실제 빈 띄우는 것이 아님)

	private MemberService memberService;
	private MemberRepository memberRepository;
	private JwtTokenProvider jwtTokenProvider;

	@BeforeEach
	void setup() {
		jwtTokenProvider = new JwtTokenProvider();
		memberRepository = new MemberFakeRepository();
		memberService = new MemberService(memberRepository, jwtTokenProvider);
	}

	@Test
	void 로그인_토큰을_생성한다() {
		// given
		Member member = 멤버_생성();
		LoginRequest req = new LoginRequest(member.getMemberDescription().getLoginId(),
			member.getMemberDescription().getPassword());
		memberRepository.save(member);

		// when
		String token = memberService.getLoginToken(req);

		// then
		Jws<Claims> jwtClaims = Jwts.parserBuilder()
			.setSigningKey(SECRET)
			.build()
			.parseClaimsJws(token);
		assertSoftly(softly -> {
			softly.assertThat(jwtClaims.getBody()).isNotNull();
			softly.assertThat(jwtClaims.getBody().get("id", Long.class)).isEqualTo(1L);
		});

	}
}
