package com.mju.mentoring.member.service;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) { // 생성자를 통한 의존성 주입
        this.memberRepository = memberRepository;
    }

    public Optional<Member> authenticate(String username, String password) { // 사용자 이름과 비밀번호를 받아 인증
        return memberRepository.findByUsernameAndPassword(username, password); // 인증에 성공하면 해당 멤버, 실패하면 빈 Optional을 반환
    }
}
