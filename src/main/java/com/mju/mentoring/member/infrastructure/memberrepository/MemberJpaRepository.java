package com.mju.mentoring.member.infrastructure.memberrepository;

import com.mju.mentoring.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNickname(final String nickname);
}
