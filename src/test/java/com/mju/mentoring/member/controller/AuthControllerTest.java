package com.mju.mentoring.member.controller;

import static com.mju.mentoring.member.fixture.MemberFixtures.회원_id_있음;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.service.AuthService;
import com.mju.mentoring.member.service.dto.LoginRequest;
import com.mju.mentoring.member.service.dto.SignupRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    void 회원가입() throws Exception {
        // given
        String nickname = "nickname";
        String username = "username";
        String password = "password";
        SignupRequest request = new SignupRequest(nickname, username, password);
        Member newMember = 회원_id_있음();
        when(authService.signup(request)).thenReturn(newMember);

        // when & then
        mockMvc.perform(post("/auth/signup")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newMember.getId()))
                .andDo(print());
    }

    @Test
    void 쿠키_로그인() throws Exception {
        // given
        String cookieName = "AUTH";
        String username = "username";
        String password = "password";
        LoginRequest request = new LoginRequest(username, password);
        Member savedMember = 회원_id_있음();
        when(authService.nonJwtLogin(request)).thenReturn(savedMember);

        // when & then
        mockMvc.perform(post("/auth/login/cookie")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(cookie().exists(cookieName))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 세션_로그인() throws Exception {
        // given
        String sessionKey = "member";
        String username = "username";
        String password = "password";
        LoginRequest request = new LoginRequest(username, password);
        Member savedMember = 회원_id_있음();
        when(authService.nonJwtLogin(request)).thenReturn(savedMember);

        // when & then
        HttpSession session = mockMvc.perform(post("/auth/login/session")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getRequest()
                .getSession();
        assertNotNull(session.getAttribute(sessionKey));
    }

    @Test
    void JWT_로그인() throws Exception {
        // given
        String username = "username";
        String password = "password";
        LoginRequest request = new LoginRequest(username, password);
        Member savedMember = 회원_id_있음();
        String token = savedMember.getNickname() + "token";
        when(authService.jwtLogin(request)).thenReturn(token);

        // when & then
        mockMvc.perform(post("/auth/login/jwt")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andDo(print());
    }
}
