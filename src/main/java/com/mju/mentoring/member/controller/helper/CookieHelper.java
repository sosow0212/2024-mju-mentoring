package com.mju.mentoring.member.controller.helper;

import jakarta.servlet.http.Cookie;

public class CookieHelper {

    private static final String COOKIE_DIVIDER = ".";
    private static final String COOKIE_NAME = "AUTH";
    private static final String COOKIE_PATH = "/";
    private static final int COOKIE_EXPIRE_SECONDS = 3600;

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
}
