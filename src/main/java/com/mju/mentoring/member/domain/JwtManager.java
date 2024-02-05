package com.mju.mentoring.member.domain;

public interface JwtManager {

    String generateToken(final String username);
    String extractNickname(final String token);
}
