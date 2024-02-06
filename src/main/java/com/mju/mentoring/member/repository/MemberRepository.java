package com.mju.mentoring.member.repository;

import com.mju.mentoring.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username); // 사용자 이름으로 멤버를 조회

    Optional<Member> findByUsernameAndPassword(String username, String password); // 사용자 이름과 비밀번호로 인증을 위한 멤버 조회
}
