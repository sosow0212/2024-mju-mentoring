package com.mju.mentoring.exam.board.infrastructure;

import com.mju.mentoring.exam.board.domain.Board;
import com.mju.mentoring.exam.board.domain.Member;
import com.mju.mentoring.exam.board.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        return memberJpaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<Member> findByMemberId(String memberId) {
        return memberJpaRepository.findMemberByMemberDescription_MemberId(memberId);

    }
}
