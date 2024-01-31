package com.mju.mentoring.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class AuthInformation {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    protected AuthInformation() {

    }

    private AuthInformation(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public static AuthInformation of(final String username, final String password) {
        return new AuthInformation(username, password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AuthInformation that = (AuthInformation) object;
        return Objects.equals(username, that.username) && Objects.equals(password,
            that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
