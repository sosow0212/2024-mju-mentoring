package com.mju.mentoring.member.domain;

public interface JwtManager {

    String generateToken(final String username);
    String extractUsername(final String token);
}
