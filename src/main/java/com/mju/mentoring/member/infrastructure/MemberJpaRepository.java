package com.mju.mentoring.member.infrastructure;

import com.mju.mentoring.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.memberAuth.username = :username")
    Optional<Member> findByUsername(@Param("username") String username);
}
