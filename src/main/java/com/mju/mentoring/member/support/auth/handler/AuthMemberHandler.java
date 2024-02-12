package com.mju.mentoring.member.support.auth.handler;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthMemberHandler {

    boolean handleRequest(final HttpServletRequest request);
    Member extractMember(final MemberService memberService, final HttpServletRequest request);
}
