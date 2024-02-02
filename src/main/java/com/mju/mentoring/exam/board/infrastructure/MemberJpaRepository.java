package com.mju.mentoring.exam.board.infrastructure;

import com.mju.mentoring.exam.board.domain.Board;
import com.mju.mentoring.exam.board.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByMemberDescription_MemberId(String memberId);
}
