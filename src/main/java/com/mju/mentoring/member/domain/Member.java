package com.mju.mentoring.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Embedded
    AuthInformation authInformation;

    @Column(unique = true, nullable = false)
    String nickname;

    protected Member() {

    }

    private Member(final AuthInformation authInformation, final String nickname) {
        this.authInformation = authInformation;
        this.nickname = nickname;
    }

    public static Member of(final String username, final String password, final String nickname) {
        return new Member(AuthInformation.of(username, password), nickname);
    }
}
