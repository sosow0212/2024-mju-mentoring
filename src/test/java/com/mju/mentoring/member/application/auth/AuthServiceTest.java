package com.mju.mentoring.member.application.auth;

import static com.mju.mentoring.member.fixture.MemberFixture.id_없는_멤버_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.mju.mentoring.member.application.auth.dto.SignInRequest;
import com.mju.mentoring.member.application.auth.dto.SignupRequest;
import com.mju.mentoring.member.application.member.MemberService;
import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.domain.MemberRepository;
import com.mju.mentoring.member.exception.exceptions.DuplicateNicknameException;
import com.mju.mentoring.member.exception.exceptions.DuplicateUsernameException;
import com.mju.mentoring.member.exception.exceptions.MemberNotFoundException;
import com.mju.mentoring.member.exception.exceptions.WrongPasswordException;
import com.mju.mentoring.member.fake.FakeFixedTokenManager;
import com.mju.mentoring.member.fake.FakeMemberRepository;
import com.mju.mentoring.member.ui.auth.dto.TokenResponse;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AuthServiceTest {

    private static final String MEMBER_DEFAULT_USERNAME = "id";
    private static final String MEMBER_DEFAULT_NICKNAME = "nickname";
    private static final String MEMBER_DEFAULT_PASSWORD = "password";
    private static final String FIXED_TOKEN = "1";

    private AuthService authService;
    private MemberRepository memberRepository;

    @BeforeEach
    void init() {
        memberRepository = new FakeMemberRepository();
        authService = new AuthService(memberRepository, new FakeFixedTokenManager(),
            new MemberService(memberRepository));
    }

    @Test
    void 회원가입_테스트() {
        // given
        Member member = id_없는_멤버_생성();

        // when
        authService.signup(new SignupRequest(MEMBER_DEFAULT_USERNAME, MEMBER_DEFAULT_NICKNAME,
            MEMBER_DEFAULT_PASSWORD));

        // then
        Optional<Member> findMember = memberRepository.findByUsername(MEMBER_DEFAULT_USERNAME);
        assertSoftly(softly -> {
            softly.assertThat(findMember).isNotEmpty();
            softly.assertThat(findMember.get()).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(member);
        });
    }

    @Test
    void 중복되는_아이디가_있는_경우_예외_처리() {
        //given
        memberRepository.save(id_없는_멤버_생성());

        // when & then
        assertThatThrownBy(() -> authService.signup(getSignupRequestDto()))
            .isInstanceOf(DuplicateUsernameException.class);
    }

    @Test
    void 중복되는_이름이_있는_경우_예외_처리() {
        // given
        memberRepository.save(id_없는_멤버_생성());

        // when & then
        assertThatThrownBy(() -> authService.signup(
            new SignupRequest("other name", MEMBER_DEFAULT_NICKNAME, MEMBER_DEFAULT_PASSWORD)))
            .isInstanceOf(DuplicateNicknameException.class);
    }

    @Test
    void 로그인_테스트() {
        // given
        memberRepository.save(id_없는_멤버_생성());

        // when
        TokenResponse response = authService.signIn(
            new SignInRequest(MEMBER_DEFAULT_USERNAME, MEMBER_DEFAULT_PASSWORD));

        // then
        assertThat(response.accessToken())
            .isEqualTo(FIXED_TOKEN);
    }

    @Test
    void 비밀번호가_틀릴_경우_예외_처리() {
        // given
        memberRepository.save(id_없는_멤버_생성());

        // when & then
        assertThatThrownBy(
            () -> authService.signIn(new SignInRequest(MEMBER_DEFAULT_USERNAME, "wrongPassword")))
            .isInstanceOf(WrongPasswordException.class);
    }

    @Test
    void 로그인시_해당_멤버가_없을_경우_예외_처리() {
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
