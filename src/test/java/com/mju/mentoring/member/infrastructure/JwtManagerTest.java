package com.mju.mentoring.member.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.mju.mentoring.member.domain.JwtManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class JwtManagerTest {

    private JwtManager jwtManager;

    @BeforeEach
    void init() {
        jwtManager = new TestJwtManager();
    }

    @Test
    void 토큰_생성() {
        // given
        String nickname = "nickname";

        // when
        String token = jwtManager.generateToken(nickname);

        // then
        assertThat(token).isNotNull();
    }

    @Test
    void 닉네임_추출() {
        // given
        String nickname = "nickname";
        String token = jwtManager.generateToken(nickname);

        // when
        String extractNickname = jwtManager.extractNickname(token);

        // then
        assertThat(nickname).isEqualTo(extractNickname);
    }
}
