package com.mju.mentoring.member.domain;

import java.util.Optional;

public interface MemberRepository {

    void save(final Member member);

    boolean existsByUsername(final String username);

    boolean existsByNickname(final String nickname);

    Optional<Member> findByUsername(final String username);
}
