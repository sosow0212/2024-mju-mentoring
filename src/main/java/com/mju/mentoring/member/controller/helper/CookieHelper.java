package com.mju.mentoring.member.controller.helper;

import com.mju.mentoring.member.exception.exceptions.CookieException;
import com.mju.mentoring.member.service.dto.AuthRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class CookieHelper {

    private static final String COOKIE_DIVIDER = ".";
    private static final String COOKIE_NAME = "AUTH";
    private static final String COOKIE_PATH = "/";
    private static final int COOKIE_EXPIRE_SECONDS = 3600;
    private static final String COOKIE_SPLITTER = "\\.";
    private static final int NICKNAME_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;

    public static Cookie generateCookieByMemberProperties(final String username, final String password) {
        String cookieValue = generateCookieValueByMemberProperties(username, password);
        Cookie cookie = new Cookie(COOKIE_NAME, cookieValue);
        saveCookieProperties(cookie);

        return cookie;
    }

    private static String generateCookieValueByMemberProperties(final String username, final String password) {
        return username + COOKIE_DIVIDER + password;
    }

    private static void saveCookieProperties(final Cookie cookie) {
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath(COOKIE_PATH);
        cookie.setMaxAge(COOKIE_EXPIRE_SECONDS);
    }

    public static AuthRequest convertServletRequestToAuthRequest(final HttpServletRequest request) {
        Cookie loginCookie = Arrays.stream(request.getCookies())
                .filter(CookieHelper::isCookieNameValid)
                .findFirst()
                .orElseThrow(CookieException::new);
        return convertCookieToAuthRequest(loginCookie);
    }

    private static boolean isCookieNameValid(final Cookie cookie) {
        String cookieName = cookie.getName();
        return cookieName.equals(COOKIE_NAME);
    }

    private static AuthRequest convertCookieToAuthRequest(final Cookie cookie) {
        String cookieValue = cookie.getValue();
        String[] splitValues = cookieValue.split(COOKIE_SPLITTER);
        String nickname = splitValues[NICKNAME_INDEX];
        String password = splitValues[PASSWORD_INDEX];

        return new AuthRequest(nickname, password);
    }
}
