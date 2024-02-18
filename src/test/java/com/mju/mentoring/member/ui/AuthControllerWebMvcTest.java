package com.mju.mentoring.member.ui;


import static com.mju.mentoring.global.CustomRestDocsHandler.customDocument;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.mentoring.global.BaseControllerWebMvcTest;
import com.mju.mentoring.member.application.auth.dto.SignInRequest;
import com.mju.mentoring.member.application.auth.dto.SignupRequest;
import com.mju.mentoring.member.ui.auth.dto.TokenResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

public class AuthControllerWebMvcTest extends BaseControllerWebMvcTest {

    private static final String MEMBER_DEFAULT_USERNAME = "id";
    private static final String MEMBER_DEFAULT_NICKNAME = "nickname";
    private static final String MEMBER_DEFAULT_PASSWORD = "password";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void 로그인_테스트() throws Exception {
        // given
        SignInRequest request = new SignInRequest(MEMBER_DEFAULT_USERNAME, MEMBER_DEFAULT_PASSWORD);
        TokenResponse response = new TokenResponse("access token");
        given(authService.signIn(request))
            .willReturn(response);

        // when & then
        mockMvc.perform(post("/auth/signin")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(customDocument("signin",
                requestFields(
                    fieldWithPath("username").type(JsonFieldType.STRING).description("아이디"),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                ),
                responseFields(
                    fieldWithPath("accessToken").type(JsonFieldType.STRING).description("액세스 토큰")
                )));
    }

    @Test
    void 회원가입_테스트() throws Exception {
        // given
        SignupRequest request = new SignupRequest(MEMBER_DEFAULT_USERNAME, MEMBER_DEFAULT_NICKNAME,
            MEMBER_DEFAULT_PASSWORD);
        willDoNothing().given(authService).signup(request);

        // when & then
        mockMvc.perform(post("/auth/signup")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(customDocument("signup",
                requestFields(
                    fieldWithPath("username").type(JsonFieldType.STRING).description("아이디"),
                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                )));
    }
}
