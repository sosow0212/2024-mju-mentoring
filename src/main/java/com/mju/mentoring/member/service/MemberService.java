package com.mju.mentoring.member.service;

import static com.mju.mentoring.member.service.helper.MemberServiceHelper.findMemberByAuth;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.domain.MemberRepository;
import com.mju.mentoring.member.exception.exceptions.MemberNotFoundException;
import com.mju.mentoring.member.domain.JwtManager;
import com.mju.mentoring.member.service.dto.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final JwtManager jwtManager;
    private final MemberRepository memberRepository;

    public Member getProfileWithAuthRequest(final AuthRequest request) {
       return findMemberByAuth(memberRepository, request);
    }

    public Member getProfileWithJwt(final String token) {
        String nickname = jwtManager.extractNickname(token);
        return memberRepository.findByNickname(nickname)
                .orElseThrow(MemberNotFoundException::new);
    }
}
