package com.mju.mentoring.member.infrastructure;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.domain.MemberRepository;
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
}
