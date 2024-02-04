package com.mju.mentoring.member.infrastructure;

import com.mju.mentoring.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Member save(final Member member);

    boolean existsByAuthInformationUsername(final String username);

    boolean existsByNickname(final String nickname);
}
