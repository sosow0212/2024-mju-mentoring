package com.mju.mentoring.member.infrastructure.memberrepository;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaMemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member save(final Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public Optional<Member> findByUsername(final String username) {
        return memberJpaRepository.findByUsername(username);
    }

    @Override
    public void deleteById(final Long memberId) {
        memberJpaRepository.deleteById(memberId);
    }
}
