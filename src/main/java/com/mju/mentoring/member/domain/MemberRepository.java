package com.mju.mentoring.member.domain;

import java.util.Optional;

public interface MemberRepository {

    Member save(final Member member);

    Optional<Member> findByUsername(final String username);

    void deleteById(final Long memberId);
}
