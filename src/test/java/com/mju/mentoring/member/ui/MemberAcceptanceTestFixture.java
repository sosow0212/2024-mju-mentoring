package com.mju.mentoring.member.ui;

import com.mju.mentoring.global.BaseAcceptanceTest;
import com.mju.mentoring.member.application.auth.dto.SignupRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;

public class MemberAcceptanceTestFixture extends BaseAcceptanceTest {

    SignupRequest 회원가입_요청() {
        return new SignupRequest("username", "nickname", "password");
    }

    ExtractableResponse 회원가입(final SignupRequest request, final String url) {
        return RestAssured.given().log().all()
            .given()
            .body(request)
            .contentType(ContentType.JSON)
            .when()
            .post(url)
            .then()
            .extract();
    }
}
