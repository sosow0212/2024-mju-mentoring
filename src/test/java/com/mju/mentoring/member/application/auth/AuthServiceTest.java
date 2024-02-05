package com.mju.mentoring.member.application.auth;

import static com.mju.mentoring.member.fixture.MemberFixture.id_없는_멤버_생성;
import static com.mju.mentoring.member.fixture.MemberFixture.멤버_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.mju.mentoring.member.application.auth.dto.SignInRequest;
import com.mju.mentoring.member.application.auth.dto.SignupRequest;
import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.domain.MemberRepository;
import com.mju.mentoring.member.domain.TokenManager;
import com.mju.mentoring.member.exception.exceptions.DuplicateNicknameException;
import com.mju.mentoring.member.exception.exceptions.DuplicateUsernameException;
import com.mju.mentoring.member.exception.exceptions.MemberNotFoundException;
import com.mju.mentoring.member.exception.exceptions.WrongPasswordException;
import com.mju.mentoring.member.ui.auth.dto.TokenResponse;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(SpringExtension.class)
class AuthServiceTest {

    private static final String MEMBER_DEFAULT_USERNAME = "id";
    private static final String MEMBER_DEFAULT_PASSWORD = "password";
    private static final String MEMBER_DEFAULT_NICKNAME = "nickname";

    @Mock
    private TokenManager tokenManager;
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private AuthService authService;
    @Captor
    private ArgumentCaptor<Member> memberCaptor;

    @Test
    void 회원가입_테스트() {
        // given
        Member member = id_없는_멤버_생성();

        // when
        authService.signup(getSignupRequestDto());

        then(memberRepository).should()
            .save(memberCaptor.capture());

        // then
        assertThat(memberCaptor.getValue()).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(member);
    }

    @Test
    void 중복되는_아이디가_있는_경우_예외_처리() {
        // given
        given(memberRepository.existsByUsername(MEMBER_DEFAULT_USERNAME))
            .willReturn(true);

        // when & then
        assertThatThrownBy(() -> authService.signup(
            getSignupRequestDto()))
            .isInstanceOf(DuplicateUsernameException.class);
    }

    @Test
    void 중복되는_이름이_있는_경우_예외_처리() {
        // given
        given(memberRepository.existsByNickname(MEMBER_DEFAULT_NICKNAME))
            .willReturn(true);

        // when & then
        assertThatThrownBy(() -> authService.signup(
            getSignupRequestDto()))
            .isInstanceOf(DuplicateNicknameException.class);
    }

    @Test
    void 로그인_테스트() {
        // given
        String token = "token";
        Member member = id_없는_멤버_생성();
        given(tokenManager.createAccessToken(member.getId()))
            .willReturn(token);
        given(memberRepository.findByUsername(MEMBER_DEFAULT_USERNAME))
            .willReturn(Optional.of(member));

        // when
        TokenResponse response = authService.signIn(
            new SignInRequest(MEMBER_DEFAULT_USERNAME, MEMBER_DEFAULT_PASSWORD));

        // then
        assertThat(response.accessToken())
            .isEqualTo(token);
    }

    @Test
    void 비밀번호가_틀릴_경우_예외_처리() {
        // given
        Member findMember = 멤버_생성();
        given(memberRepository.findByUsername(MEMBER_DEFAULT_USERNAME))
            .willReturn(Optional.of(findMember));

        // when & then
        assertThatThrownBy(
            () -> authService.signIn(new SignInRequest(MEMBER_DEFAULT_USERNAME, "wrongPassword")))
            .isInstanceOf(WrongPasswordException.class);
    }

    @Test
    void 로그인시_해당_멤버가_없을_경우_예외_처리() {
        // given
        given(memberRepository.findByUsername(MEMBER_DEFAULT_USERNAME))
            .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(
            () -> authService.signIn(
                new SignInRequest(MEMBER_DEFAULT_USERNAME, MEMBER_DEFAULT_PASSWORD)))
            .isInstanceOf(MemberNotFoundException.class);
    }

    private static SignupRequest getSignupRequestDto() {
        return new SignupRequest(MEMBER_DEFAULT_USERNAME, MEMBER_DEFAULT_NICKNAME,
            MEMBER_DEFAULT_PASSWORD);
    }
}
