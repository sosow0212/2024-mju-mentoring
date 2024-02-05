package com.mju.mentoring.member.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.mju.mentoring.member.domain.PasswordManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class PasswordManagerTest {

    private PasswordManager passwordManager;

    @BeforeEach
    void init() {
        passwordManager = new TestPasswordManager();
    }

    @Test
    void 비밀번호_암호화() {
        // given
        String originPassword = "hello";

        // when
        String encodedPassword = passwordManager.encode(originPassword);

        // then
        assertThat(encodedPassword).isNotEqualTo(originPassword);
    }
}
