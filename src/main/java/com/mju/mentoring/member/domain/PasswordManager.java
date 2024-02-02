package com.mju.mentoring.member.domain;

public interface PasswordManager {

    String encode(final String plainPassword);
}
