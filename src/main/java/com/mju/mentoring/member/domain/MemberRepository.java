package com.mju.mentoring.member.domain;

public interface MemberRepository {

    void save(final Member member);

    boolean isExistByUsername(final String username);

    boolean isExistByNickname(final String nickname);
}
