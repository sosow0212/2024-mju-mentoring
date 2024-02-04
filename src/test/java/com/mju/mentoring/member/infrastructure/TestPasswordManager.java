package com.mju.mentoring.member.infrastructure;

import com.mju.mentoring.member.domain.PasswordManager;

public class TestPasswordManager implements PasswordManager {

    private static final String ENCRYPT_KEY = "encrypt";

    @Override
    public String encode(final String plainPassword) {
        return plainPassword + ENCRYPT_KEY;
    }
}
