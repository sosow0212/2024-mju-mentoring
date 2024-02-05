package com.mju.mentoring.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AuthInformationTest {

    @Test
    void 비밀번호가_일치하지_않을_경우_테스트() {
        // given
        AuthInformation authInformation = AuthInformation.of("id", "password");

        // when
        boolean result = authInformation.isValidPassword("wrong password");

        // then
        assertThat(result).isFalse();
    }
}
