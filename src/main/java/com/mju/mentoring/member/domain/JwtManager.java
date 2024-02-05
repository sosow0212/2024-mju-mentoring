package com.mju.mentoring.member.domain;

public interface JwtManager {

    String generateToken(final String nickname);
    String extractNickname(final String token);
}
