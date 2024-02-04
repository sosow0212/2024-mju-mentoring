package com.mju.mentoring.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.domain.MemberRepository;
import com.mju.mentoring.member.domain.PasswordManager;
import com.mju.mentoring.member.infrastructure.MemberTestRepository;
import com.mju.mentoring.member.infrastructure.TestPasswordManager;
import com.mju.mentoring.member.service.dto.LoginRequest;
import com.mju.mentoring.member.service.dto.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class AuthServiceTest {

    private PasswordManager passwordManager;
    private MemberRepository memberRepository;
    private AuthService authService;

    @BeforeEach
    void init() {
        passwordManager = new TestPasswordManager();
        memberRepository = new MemberTestRepository();
        authService = new AuthService(passwordManager, memberRepository);
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
    void 회원_로그인() {
        // given
        String nickname = "nickname";
        String username = "username";
        String password = "password";
        SignupRequest request = new SignupRequest(nickname, username, password);
        Member saveMember = authService.signup(request);
        LoginRequest loginRequest = new LoginRequest(username, password);

        // when
        Member loginMember = authService.login(loginRequest);

        // then
        assertThat(saveMember)
                .usingRecursiveComparison()
                .isEqualTo(loginMember);
    }
}
