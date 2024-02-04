package com.mju.mentoring.member.domain;

import com.mju.mentoring.member.exception.exceptions.PasswordNotMatchException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class MemberAuth {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public void validatePassword(final String inputPassword) {
        if(!password.equals(inputPassword)) {
            throw new PasswordNotMatchException();
        }
    }

    public MemberAuth updatePassword(final String password) {
        return new MemberAuth(username, password);
    }

    public boolean isSameUsername(final String username) {
        return this.username.equals(username);
    }
}
