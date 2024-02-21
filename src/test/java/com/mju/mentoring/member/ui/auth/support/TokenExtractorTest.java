package com.mju.mentoring.member.ui.auth.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mju.mentoring.member.exception.exceptions.InvalidTokenException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class TokenExtractorTest {

    @Test
    void 헤더의_인증_부분이_null일_경우_예외_처리() {
        // when & then
        assertThatThrownBy(() -> TokenExtractor.extractToken(Optional.empty()))
            .isInstanceOf(InvalidTokenException.class);
    }

    @Test
    void Bearer_토큰이_아닐_경우_예외_처리() {
        // given
        String invalidToken = "invalid token";

        // when & then
        assertThatThrownBy(() -> TokenExtractor.extractToken(Optional.of(invalidToken)))
            .isInstanceOf(InvalidTokenException.class);
    }

    @Test
    void 토큰의_형식이_유효하지_않을_때_예외처리() {
        // given
        String invalidToken = "invalidToken";

        // when & then
        assertThatThrownBy(() -> TokenExtractor.extractToken(Optional.of(invalidToken)))
            .isInstanceOf(InvalidTokenException.class);
    }

    @Test
    void Bearer_토큰_추출_테스트() {
        // given
        String bearerPrefix = "Bearer ";
        String token = "token";

        // when
        String extractToken = TokenExtractor.extractToken(Optional.of(bearerPrefix + token));

        // then
        assertThat(extractToken).isEqualTo(token);
    }
}
