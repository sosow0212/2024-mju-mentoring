package com.mju.mentoring.member.support.auth.handler;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.service.MemberService;
import com.mju.mentoring.member.service.dto.AuthRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class CookieAuthMemberHandler implements AuthMemberHandler {

    private static final String COOKIE_NAME = "AUTH";
    private static final String COOKIE_SPLITTER = "\\.";
    private static final int NICKNAME_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;

    @Override
    public boolean handleRequest(final HttpServletRequest webRequest) {
        Cookie loginCookie = extractCookieFromServletRequest(webRequest);
        if (loginCookie == null) {
            return false;
        }
        return true;
    }

    private Cookie extractCookieFromServletRequest(final HttpServletRequest webRequest) {
        if (webRequest.getCookies() == null) {
            return null;
        }
        return Arrays.stream(webRequest.getCookies())
                .filter(this::isCookieNameValid)
                .findFirst()
                .orElse(null);
    }

    private boolean isCookieNameValid(final Cookie cookie) {
        String cookieName = cookie.getName();
        return cookieName.equals(COOKIE_NAME);
    }

    @Override
    public Member extractMember(final MemberService memberService, final HttpServletRequest webRequest) {
        Cookie loginCookie = extractCookieFromServletRequest(webRequest);
        AuthRequest authRequest = convertCookieToAuthRequest(loginCookie);
        return memberService.getProfileWithAuthRequest(authRequest);
    }

    private static AuthRequest convertCookieToAuthRequest(final Cookie cookie) {
        String cookieValue = cookie.getValue();
        String[] splitValues = cookieValue.split(COOKIE_SPLITTER);
        String nickname = splitValues[NICKNAME_INDEX];
        String password = splitValues[PASSWORD_INDEX];

        return new AuthRequest(nickname, password);
    }
}
