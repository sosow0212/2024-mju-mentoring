package com.mju.mentoring.exam.board.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mju.mentoring.exam.board.domain.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

	Optional<Member> findMemberByMemberDescriptionLoginId(String memberId);
}
