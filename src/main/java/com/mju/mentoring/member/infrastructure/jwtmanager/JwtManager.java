package com.mju.mentoring.member.infrastructure.jwtmanager;

public interface JwtManager {

    String generateToken(final String username);
    String extractUsername(final String token);
}
