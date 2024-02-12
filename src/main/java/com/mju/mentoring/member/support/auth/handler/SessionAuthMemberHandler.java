package com.mju.mentoring.member.support.auth.handler;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.service.MemberService;
import com.mju.mentoring.member.service.dto.AuthRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionAuthMemberHandler implements AuthMemberHandler {

    private static final String SESSION_KEY = "member";

    @Override
    public boolean handleRequest(final HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null) {
            return false;
        }
        AuthRequest authRequest = (AuthRequest) session.getAttribute(SESSION_KEY);
        if (authRequest == null) {
            return false;
        }
        return true;
    }

    @Override
    public Member extractMember(final MemberService memberService, final HttpServletRequest request) {
        HttpSession session = request.getSession();
        AuthRequest authRequest = (AuthRequest) session.getAttribute(SESSION_KEY);
        return memberService.getProfileWithAuthRequest(authRequest);
    }
}
