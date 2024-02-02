package com.mju.mentoring.exam.board.domain;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(final Member member);

    Optional<Member> findById(final Long id);

    List<Member> findAll();

    void deleteById(final Long id);
    Optional<Member> findByMemberId(final String memberId);

}
