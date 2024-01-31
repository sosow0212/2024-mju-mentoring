package com.mju.mentoring.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class MemberLogin {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    public MemberLogin updatePassword(final String password) {
        return new MemberLogin(username, password);
    }
}
