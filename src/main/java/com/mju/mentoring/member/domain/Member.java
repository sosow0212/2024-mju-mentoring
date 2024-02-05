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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Embedded
    private MemberAuth memberAuth;

    public Member(final String nickname, final MemberAuth memberAuth) {
        this.nickname = nickname;
        this.memberAuth = memberAuth;
    }

    public void validatePassword(final String password) {
        memberAuth.validatePassword(password);
    }

    public boolean isSameNickname(final String nickname) {
        return this.nickname.equals(nickname);
    }

    public String getUsername() {
        return memberAuth.getUsername();
    }

    public String getPassword() {
        return memberAuth.getPassword();
    }
}
