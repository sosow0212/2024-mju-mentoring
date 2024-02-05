package com.mju.mentoring.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class AuthInformation {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    public static AuthInformation of(final String username, final String password) {
        return new AuthInformation(username, password);
    }

    public boolean isValidPassword(final String password) {
        return this.password.equals(password);
    }
}
