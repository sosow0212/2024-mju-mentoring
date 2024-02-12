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

import com.mju.mentoring.member.service.MemberService;

import com.mju.mentoring.member.service.dto.LoginRequest;
import com.mju.mentoring.member.service.dto.SignupRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import java.util.stream.Stream;

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

    @MockBean
    private MemberService memberService;

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

    @Nested
    class 예외_테스트 {

        @ParameterizedTest(name = "닉네임이 [{0}], username이 [{1}], 비밀번호가 [{2}]인 경우")
        @MethodSource(value = "exceptionSignupSource")
        void 회원가입_시_빈_값이나_null은_안_된다(final String nickname, final String username, final String password)
                throws Exception {
            // given
            SignupRequest request = new SignupRequest(nickname, username, password);
            Member newMember = 회원_id_있음();
            when(authService.signup(request)).thenReturn(newMember);

            // when & then
            mockMvc.perform(post("/auth/signup")
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").exists())
                    .andDo(print());
        }

        private static Stream<String[]> exceptionSignupSource() {
            return Stream.of(
                    new String[]{"", "username", "password"},
                    new String[]{null, "username", "password"},
                    new String[]{"nickname", "", "password"},
                    new String[]{"nickname", null, "password"},
                    new String[]{"nickname", "username", ""},
                    new String[]{"nickname", "username", null},
                    new String[]{"", "", ""},
                    new String[]{null, null, null}
            );
        }

        @ParameterizedTest(name = "username이 [{0}], 비밀번호가 [{1}]인 경우")
        @MethodSource(value = "exceptionLoginSource")
        void 쿠키_로그인_시_빈_값이나_null은_안_된다(final String nickname, final String password)
                throws Exception {
            // given
            LoginRequest request = new LoginRequest(nickname, password);
            Member newMember = 회원_id_있음();
            when(authService.nonJwtLogin(request)).thenReturn(newMember);

            // when & then
            mockMvc.perform(post("/auth/login/cookie")
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").exists())
                    .andDo(print());
        }

        private static Stream<String[]> exceptionLoginSource() {
            return Stream.of(
                    new String[]{"nickname", ""},
                    new String[]{"", "password"},
                    new String[]{null, "password"},
                    new String[]{"nickname", null},
                    new String[]{"", ""},
                    new String[]{null, null}
            );
        }

        @ParameterizedTest(name = "username이 [{0}], 비밀번호가 [{1}]인 경우")
        @MethodSource(value = "exceptionLoginSource")
        void 세션_로그인_시_빈_값이나_null은_안_된다(final String nickname, final String password)
                throws Exception {
            // given
            LoginRequest request = new LoginRequest(nickname, password);
            Member newMember = 회원_id_있음();
            when(authService.nonJwtLogin(request)).thenReturn(newMember);

            // when & then
            mockMvc.perform(post("/auth/login/session")
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").exists())
                    .andDo(print());
        }

        @ParameterizedTest(name = "username이 [{0}], 비밀번호가 [{1}]인 경우")
        @MethodSource(value = "exceptionLoginSource")
        void JWT_로그인_시_빈_값이나_null은_안_된다(final String nickname, final String password)
                throws Exception {
            // given
            LoginRequest request = new LoginRequest(nickname, password);
            Member newMember = 회원_id_있음();
            when(authService.nonJwtLogin(request)).thenReturn(newMember);

            // when & then
            mockMvc.perform(post("/auth/login/jwt")
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").exists())
                    .andDo(print());
        }
    }
}
