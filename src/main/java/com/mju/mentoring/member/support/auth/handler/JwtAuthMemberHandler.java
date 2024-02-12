package com.mju.mentoring.member.support.auth.handler;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthMemberHandler implements AuthMemberHandler {

    private static final String JWT_HEADER_NAME = "Authorization";

    @Override
    public boolean handleRequest(final HttpServletRequest request) {
        String token = request.getHeader(JWT_HEADER_NAME);
        if (token == null) {
            return false;
        }
        return true;
    }

    @Override
    public Member extractMember(final MemberService memberService, final HttpServletRequest request) {
        String token = request.getHeader(JWT_HEADER_NAME);
        return memberService.getProfileWithJwt(token);
    }
}
