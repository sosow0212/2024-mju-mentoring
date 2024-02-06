package com.mju.mentoring.member.service;

import static com.mju.mentoring.member.fixture.MemberFixtures.회원_id_있음;
import static org.assertj.core.api.Assertions.assertThat;

import com.mju.mentoring.member.domain.JwtManager;
import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.domain.MemberRepository;
import com.mju.mentoring.member.infrastructure.MemberTestRepository;
import com.mju.mentoring.member.infrastructure.TestJwtManager;
import com.mju.mentoring.member.service.dto.AuthRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class MemberServiceTest {

    private JwtManager jwtManager;
    private MemberRepository memberRepository;
    private MemberService memberService;

    @BeforeEach
    void init() {
        jwtManager = new TestJwtManager();
        memberRepository = new MemberTestRepository();
        memberService = new MemberService(jwtManager, memberRepository);
    }

    @Test
    void JWT_회원_프로필_조회() {
        // given
        Member createMember = 회원_id_있음();
        String token = jwtManager.generateToken(createMember.getNickname());
        memberRepository.save(createMember);

        // when
        Member findMember = memberService.getProfileWithJwt(token);

        // then
        assertThat(findMember).usingRecursiveComparison()
                .isEqualTo(createMember);
    }

    @Test
    void 쿠키_세션_프로필_조회() {
        // given
        Member createMember = 회원_id_있음();
        memberRepository.save(createMember);
        AuthRequest authRequest = new AuthRequest(createMember.getNickname(), createMember.getPassword());

        // when
        Member findMember = memberService.getProfileWithAuthRequest(authRequest);

        // then
        assertThat(findMember).usingRecursiveComparison()
                .isEqualTo(createMember);
    }
}
