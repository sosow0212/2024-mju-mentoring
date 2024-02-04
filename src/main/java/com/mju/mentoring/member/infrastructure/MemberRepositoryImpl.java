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
    public boolean isExistByUsername(final String username) {
        return memberJpaRepository.isExistByUsername(username);
    }

    @Override
    public boolean isExistByNickname(final String nickname) {
        return memberJpaRepository.isExistByNickname(nickname);
    }
}
