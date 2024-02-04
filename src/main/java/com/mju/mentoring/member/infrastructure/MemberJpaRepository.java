package com.mju.mentoring.member.infrastructure;

import com.mju.mentoring.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Long, Member> {

    void save(final Member member);

    boolean isExistByUsername(final String username);

    boolean isExistByNickname(final String nickname);
}
