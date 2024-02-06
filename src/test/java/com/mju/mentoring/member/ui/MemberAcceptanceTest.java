package com.mju.mentoring.member.ui;

import static com.mju.mentoring.member.fixture.MemberFixture.멤버_생성;

import com.mju.mentoring.member.domain.Member;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class MemberAcceptanceTest extends MemberAcceptanceTestFixture {

    private static final String 회원가입_url = "/auth/signup";
    private static final String 로그인_url = "/auth/signin";

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
        로그인할_멤버_저장(멤버);
        var 로그인_요청서 = 로그인_요청();

        // when
        var 로그인_결과 = 로그인(로그인_요청서, 로그인_url);

        // then
        로그인_검증(로그인_결과);
    }
}
