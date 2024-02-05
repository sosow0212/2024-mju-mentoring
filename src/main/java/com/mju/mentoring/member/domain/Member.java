package com.mju.mentoring.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Embedded
    AuthInformation authInformation;

    @Column(unique = true, nullable = false)
    String nickname;

    private Member(final AuthInformation authInformation, final String nickname) {
        this.authInformation = authInformation;
        this.nickname = nickname;
    }

    public static Member of(final String username, final String password, final String nickname) {
        return new Member(AuthInformation.of(username, password), nickname);
    }

    public boolean isValidPassword(final String password) {
        return authInformation.isValidPassword(password);
    }

    public String getUsername() {
        return authInformation.getUsername();
    }
}
