package com.mju.mentoring.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.mju.mentoring.member.domain.JwtManager;
import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.domain.MemberRepository;
import com.mju.mentoring.member.domain.PasswordManager;
import com.mju.mentoring.member.exception.exceptions.AlreadyRegisteredException;
import com.mju.mentoring.member.infrastructure.MemberTestRepository;
import com.mju.mentoring.member.infrastructure.TestJwtManager;
import com.mju.mentoring.member.infrastructure.TestPasswordManager;
import com.mju.mentoring.member.service.dto.LoginRequest;
import com.mju.mentoring.member.service.dto.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class AuthServiceTest {

    private PasswordManager passwordManager;
    private JwtManager jwtManager;
    private MemberRepository memberRepository;
    private AuthService authService;

    @BeforeEach
    void init() {
        passwordManager = new TestPasswordManager();
        jwtManager = new TestJwtManager();
        memberRepository = new MemberTestRepository();
        authService = new AuthService(passwordManager, jwtManager, memberRepository);
    }

    @Test
    void 회원을_저장한다() {
        // given
        String nickname = "nickname";
        String username = "username";
        String password = "password";
        SignupRequest request = new SignupRequest(nickname, username, password);

        // when
        Member saveMember = authService.signup(request);

        // then
        assertSoftly((softly) -> {
            softly.assertThat(saveMember.getNickname()).isEqualTo(nickname);
            softly.assertThat(saveMember.getUsername()).isEqualTo(username);
            softly.assertThat(saveMember.getPassword()).isNotEqualTo(password);
        });
    }

    @Test
    void 회원_로그인_세션_쿠키() {
        // given
        String nickname = "nickname";
        String username = "username";
        String password = "password";
        SignupRequest request = new SignupRequest(nickname, username, password);
        Member saveMember = authService.signup(request);
        LoginRequest loginRequest = new LoginRequest(nickname, password);

        // when
        Member loginMember = authService.nonJwtLogin(loginRequest);

        // then
        assertThat(saveMember)
                .usingRecursiveComparison()
                .isEqualTo(loginMember);
    }

    @Test
    void 회원_로그인_JWT() {
        // given
        String nickname = "nickname";
        String username = "username";
        String password = "password";
        SignupRequest request = new SignupRequest(nickname, username, password);
        Member member = authService.signup(request);
        LoginRequest loginRequest = new LoginRequest(nickname, password);

        // when
        String token = authService.jwtLogin(loginRequest);

        // then
        assertThat(token.contains(member.getNickname())).isTrue();
    }

    @Nested
    class 예외_테스트 {

        @Test
        void 이미_등록된_닉네임은_쓸_수_없다() {
            // given
            String nickname = "nickname";
            String username = "username";
            String password = "password";
            SignupRequest signupRequest = new SignupRequest(nickname, username, password);
            authService.signup(signupRequest);

            String newUsername = "newUsername";
            String newPassword = "newPassword";
            SignupRequest newRequest = new SignupRequest(nickname, newUsername, newPassword);

            // when & then
            assertThatThrownBy(() -> authService.signup(newRequest))
                    .isInstanceOf(AlreadyRegisteredException.class);
        }
    }
}
