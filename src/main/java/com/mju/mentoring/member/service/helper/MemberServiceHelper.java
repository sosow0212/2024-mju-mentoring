package com.mju.mentoring.member.service.helper;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.domain.MemberRepository;
import com.mju.mentoring.member.exception.MemberNotFoundException;
import com.mju.mentoring.member.service.dto.AuthRequest;

public class MemberServiceHelper {

    public static Member findMemberByAuth(final MemberRepository repository, final AuthRequest request) {
        Member member = repository.findByUsername(request.username())
                .orElseThrow(MemberNotFoundException::new);
        member.validatePassword(request.password());
    
        return member;
    }
}