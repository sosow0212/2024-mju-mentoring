package com.mju.mentoring.exam.board.domain;

import java.util.Optional;

public interface MemberRepository {

	Member save(final Member member);

	Optional<Member> findById(final Long id);

	void deleteById(final Long id);

	Optional<Member> findByLoginId(final String memberId);
}
