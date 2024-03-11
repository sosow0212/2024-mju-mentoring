package com.mju.mentoring.member.ui;

import static com.mju.mentoring.member.fixture.MemberFixture.멤버_생성;

import com.mju.mentoring.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class MemberAcceptanceTest extends MemberAcceptanceTestFixture {

    private static final String 회원가입_url = "/auth/signup";
    private static final String 로그인_url = "/auth/signin";
    private static final String 닉네임_변경_url = "/auth/nickname";

    private String 토큰;

    @BeforeEach
    void setUp() {
        토큰 = tokenManager.create(1L);
    }

    @Test
    void 회원가입_테스트() {
        // given
        var 회원가입_요청서 = 회원가입_요청();

        // when
        var 회원가입_결과 = 회원가입(회원가입_요청서, 회원가입_url);

        // then
        회원가입_검증(회원가입_결과);
    }

    @Test
    void 로그인_테스트() {
        // given
        Member 멤버 = 멤버_생성();
        멤버를_생성한다(멤버);
        var 로그인_요청서 = 로그인_요청();

        // when
        var 로그인_결과 = 로그인(로그인_요청서, 로그인_url);

        // then
        로그인_검증(로그인_결과);
    }

    @Test
    void 닉네임_변경_테스트() {
        // given
        Member 멤버 = 멤버_생성();
        멤버를_생성한다(멤버);
        var 닉네임_변경_요청서 = 닉네임_변경_요청();

        // when
        var 닉네임_변경_결과 = 닉네임_변경(닉네임_변경_요청서, 토큰, 닉네임_변경_url);

        // then
        닉네임_변경_검증(닉네임_변경_결과);
    }
}
