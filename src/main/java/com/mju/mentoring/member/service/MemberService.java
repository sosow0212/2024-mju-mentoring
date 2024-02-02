package com.mju.mentoring.member.service;

import static com.mju.mentoring.member.service.helper.MemberServiceHelper.findMemberByAuth;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.domain.MemberRepository;
import com.mju.mentoring.member.exception.MemberNotFoundException;
import com.mju.mentoring.member.service.dto.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member getProfileWithAuthRequest(final AuthRequest request) {
       return findMemberByAuth(memberRepository, request);
    }

    public Member getProfileWithUsername(final String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(MemberNotFoundException::new);
    }
}
