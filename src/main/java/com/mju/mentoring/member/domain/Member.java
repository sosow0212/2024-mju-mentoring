package com.mju.mentoring.member.domain;

import com.mju.mentoring.global.domain.BaseEntity;
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
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private AuthInformation authInformation;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Embedded
    private Point point;

    private Member(
        final AuthInformation authInformation, final String nickname, final Point point) {
        this.authInformation = authInformation;
        this.nickname = nickname;
        this.point = point;
    }

    public static Member createDefault(
        final String username, final String password, final String nickname) {
        return new Member(AuthInformation.of(username, password), nickname, Point.createDefault());
    }

    public static Member of(
        final String username, final String password, final String nickname, final Long point
    ) {
        return new Member(AuthInformation.of(username, password), nickname, Point.from(point));
    }

    public boolean isValidPassword(final String password) {
        return authInformation.isValidPassword(password);
    }

    public void changeNickName(final String newNickname) {
        this.nickname = newNickname;
    }

    public void increasePoint(final Long plusPoint) {
        point.increasePoint(plusPoint);
    }

    public String getUsername() {
        return authInformation.getUsername();
    }
}
