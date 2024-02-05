package com.mju.mentoring.member.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.mju.mentoring.global.BaseAcceptanceTest;
import com.mju.mentoring.member.application.auth.dto.SignInRequest;
import com.mju.mentoring.member.application.auth.dto.SignupRequest;
import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.domain.MemberRepository;
import com.mju.mentoring.member.ui.auth.dto.TokenResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class MemberAcceptanceTestFixture extends BaseAcceptanceTest {

    private static final String MEMBER_DEFAULT_USERNAME = "id";
    private static final String MEMBER_DEFAULT_NICKNAME = "nickname";
    private static final String MEMBER_DEFAULT_PASSWORD = "password";

    @Autowired
    MemberRepository memberRepository;

    protected void 로그인할_멤버_저장(final Member member) {
        memberRepository.save(member);
    }

    protected SignupRequest 회원가입_요청() {
        return new SignupRequest(MEMBER_DEFAULT_USERNAME, MEMBER_DEFAULT_NICKNAME,
            MEMBER_DEFAULT_PASSWORD);
    }

    protected ExtractableResponse 회원가입(final SignupRequest request, final String url) {
        return RestAssured.given().log().all()
            .given()
            .body(request)
            .contentType(ContentType.JSON)
            .when()
            .post(url)
            .then()
            .extract();
    }

    protected void 회원가입_검증(ExtractableResponse response) {
        int code = response.statusCode();

        assertThat(code).isEqualTo(HttpStatus.CREATED.value());
    }

    protected SignInRequest 로그인_요청() {
        return new SignInRequest(MEMBER_DEFAULT_USERNAME, MEMBER_DEFAULT_PASSWORD);
    }

    protected ExtractableResponse 로그인(final SignInRequest request, final String url) {
        return RestAssured.given().log().all()
            .given()
            .body(request)
            .contentType(ContentType.JSON)
            .when()
            .post(url)
            .then()
            .extract();
    }

    protected void 로그인_검증(ExtractableResponse response) {
        int code = response.statusCode();
        TokenResponse tokenResponse = response.as(TokenResponse.class);

        assertSoftly(softly -> {
            softly.assertThat(code).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(tokenResponse).isNotNull();
        });
    }
}
