package com.mju.mentoring.exam.board.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mju.mentoring.exam.board.domain.Member;
import com.mju.mentoring.exam.board.domain.MemberRepository;

import lombok.RequiredArgsConstructor;

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
		return memberJpaRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		memberJpaRepository.deleteById(id);
	}

	@Override
	public Optional<Member> findByLoginId(String memberId) {
		return memberJpaRepository.findMemberByMemberDescription_LoginId(memberId);
	}
}
