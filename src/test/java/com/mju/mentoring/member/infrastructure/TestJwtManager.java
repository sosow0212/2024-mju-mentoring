package com.mju.mentoring.member.infrastructure;

import com.mju.mentoring.member.domain.JwtManager;

public class TestJwtManager implements JwtManager {

    private static final String TOKEN_SUFFIX = "token";

    @Override
    public String generateToken(final String username) {
        return username + TOKEN_SUFFIX;
    }

    @Override
    public String extractUsername(final String token) {
        return token.replace(TOKEN_SUFFIX, "");
    }
}
