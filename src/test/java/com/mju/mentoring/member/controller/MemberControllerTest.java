package com.mju.mentoring.member.controller;

import static com.mju.mentoring.member.fixture.MemberFixtures.회원_id_있음;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.service.MemberService;
import com.mju.mentoring.member.service.dto.AuthRequest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
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

    @Test
    void 쿠키_프로필_조회() throws Exception {
        // given
        Cookie cookie = new Cookie("AUTH", "username.password");
        Member member = 회원_id_있음();
        AuthRequest request = new AuthRequest("username", "password");
        when(memberService.getProfileWithAuthRequest(request)).thenReturn(member);

        // when & then
        mockMvc.perform(get("/member/profile/cookie")
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
        AuthRequest request = new AuthRequest("username", "password");
        session.setAttribute("member", request);
        when(memberService.getProfileWithAuthRequest(request)).thenReturn(member);

        // when & then
        mockMvc.perform(get("/member/profile/session")
                .session(session))
                .andExpect(jsonPath("$.nickname").value(member.getNickname()))
                .andExpect(jsonPath("$.username").value(member.getUsername()))
                .andDo(print());
    }

    /*
    @Test
    void JWT_프로필_조회() throws Exception {
        // given
        String token = "hello";
        Member member = 회원_id_있음();

        when(memberService.getProfileWithUsername(member.getUsername())).thenReturn(member);

        // when & then
        mockMvc.perform(get("/member/profile/jwt")
                .header("Authorization", "Bearer " + token))
                .andDo(print());

        verify(memberService.getProfileWithUsername(member.getUsername()));
    }
     */
}
