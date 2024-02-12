package com.mju.mentoring.member.controller;

import static com.mju.mentoring.member.fixture.MemberFixtures.회원_id_있음;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.exception.exceptions.JwtSignatureException;
import com.mju.mentoring.member.exception.exceptions.PasswordNotMatchException;
import com.mju.mentoring.member.service.MemberService;
import com.mju.mentoring.member.service.dto.AuthRequest;
import com.mju.mentoring.member.support.auth.AuthMemberResolver;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private AuthMemberResolver authMemberResolver;

    @Test
    void 쿠키_프로필_조회() throws Exception {
        // given
        Cookie cookie = new Cookie("AUTH", "nickname.password");
        Member member = 회원_id_있음();
        AuthRequest request = new AuthRequest("nickname", "password");

        when(authMemberResolver.supportsParameter(any())).thenReturn(true);
        when(authMemberResolver.resolveArgument(any(), any(), any(), any())).thenReturn(member);
        when(memberService.getProfileWithAuthRequest(request)).thenReturn(member);
        // when & then
        mockMvc.perform(get("/member/profile")
                .cookie(cookie))
                .andExpect(jsonPath("$.nickname").value(member.getNickname()))
                .andExpect(jsonPath("$.username").value(member.getUsername()))
                .andDo(print());
    }

    @Test
    void 세션_프로필_조회() throws Exception {
        // given
        MockHttpSession session = new MockHttpSession();
        Member member = 회원_id_있음();
        AuthRequest request = new AuthRequest("nickname", "password");
        session.setAttribute("member", request);
        when(authMemberResolver.supportsParameter(any())).thenReturn(true);
        when(authMemberResolver.resolveArgument(any(), any(), any(), any())).thenReturn(member);
        when(memberService.getProfileWithAuthRequest(request)).thenReturn(member);

        // when & then
        mockMvc.perform(get("/member/profile")
                .session(session))
                .andExpect(jsonPath("$.nickname").value(member.getNickname()))
                .andExpect(jsonPath("$.username").value(member.getUsername()))
                .andDo(print());
    }

    @Test
    void JWT_프로필_조회() throws Exception {
        // given
        String token = "hello";
        Member member = 회원_id_있음();
        when(authMemberResolver.supportsParameter(any())).thenReturn(true);
        when(authMemberResolver.resolveArgument(any(), any(), any(), any())).thenReturn(member);
        // when(memberService.getProfileWithJwt(token)).thenReturn(member);

        // when & then
        mockMvc.perform(get("/member/profile")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andDo(print());
      
        // verify(memberService).getProfileWithJwt(token);
    }

    @Nested
    class 예외_테스트 {

        /*
        @Test
        public void 쿠키_프로필_예외() throws Exception {
            // given
            Cookie cookie = new Cookie("AUTH", "nickname.password");
            AuthRequest request = new AuthRequest("nickname", "1234");
            when(memberService.getProfileWithAuthRequest(request)).thenThrow(PasswordNotMatchException.class);

            // when & then
            mockMvc.perform(get("/member/profile/cookie")
                            .cookie(cookie))
                    .andExpect(result -> assertInstanceOf(PasswordNotMatchException.class, result.getResolvedException()))
                    .andDo(print());
        }
        */

        @Test
        void 세션_프로필_예외() throws Exception {
            // given
            MockHttpSession session = new MockHttpSession();
            AuthRequest request = new AuthRequest("nickname", "1234");
            session.setAttribute("member", request);
            when(authMemberResolver.supportsParameter(any())).thenReturn(false);
            when(authMemberResolver.resolveArgument(any(), any(), any(), any())).thenThrow(PasswordNotMatchException.class);

            // when & then
            mockMvc.perform(get("/member/profile")
                            .session(session))
                    .andExpect(result -> assertInstanceOf(PasswordNotMatchException.class, result.getResolvedException()))
                    .andDo(print());
        }

        @Test
        void JWT_프로필_토큰_예외() throws Exception {
            // given
            String token = "exception";
            when(authMemberResolver.supportsParameter(any())).thenReturn(false);
            when(authMemberResolver.resolveArgument(any(), any(), any(), any())).thenThrow(JwtSignatureException.class);

            // when & then
            mockMvc.perform(get("/member/profile")
                            .header("Authorization", token))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }
    }
}
