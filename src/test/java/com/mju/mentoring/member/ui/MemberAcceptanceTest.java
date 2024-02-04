package com.mju.mentoring.member.ui;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class MemberAcceptanceTest extends MemberAcceptanceTestFixture {

    @Test
    void 회원가입_테스트() {
        // given
        var 회원가입_요청서 = 회원가입_요청();

        // when
        var 회원가입_결과 = 회원가입(회원가입_요청서);

        // then
        회원가입_검증(회원가입_결과);
    }

    @Test
    void 로그인_테스트() {
        // given
        var 로그인_요청서 = 로그인_요청();

        // when
        var 로그인_결과 = 로그인(로그인_요청서);

        // then
        로그인_검증(로그인_결과);
    }
}
