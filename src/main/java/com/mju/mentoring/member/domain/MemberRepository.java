package com.mju.mentoring.member.domain;

public interface MemberRepository {

    void save(final Member member);

    boolean existsByUsername(final String username);

    boolean existsByNickname(final String nickname);
}
