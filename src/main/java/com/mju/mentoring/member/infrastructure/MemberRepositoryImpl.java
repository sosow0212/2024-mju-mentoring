package com.mju.mentoring.member.infrastructure;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.domain.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public void save(final Member member) {
        memberJpaRepository.save(member);
    }

    @Override
    public boolean existsByUsername(final String username) {
        return memberJpaRepository.existsByAuthInformationUsername(username);
    }

    @Override
    public boolean existsByNickname(final String nickname) {
        return memberJpaRepository.existsByNickname(nickname);
    }

    @Override
    public Optional<Member> findByUsername(final String username) {
        return memberJpaRepository.findMemberByAuthInformationUsername(username);
    }

    @Override
    public Optional<Member> findById(final Long id) {
        return memberJpaRepository.findById(id);
    }
}
