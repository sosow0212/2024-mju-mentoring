package com.mju.mentoring.exam.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mju.mentoring.exam.board.domain.Member;
import com.mju.mentoring.exam.board.domain.MemberRepository;
import com.mju.mentoring.exam.board.exception.MemberNotFoundException;
import com.mju.mentoring.exam.board.provider.JwtTokenProvider;
import com.mju.mentoring.exam.board.service.dto.LoginRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	private final JwtTokenProvider jwtTokenProvider;

	@Transactional(readOnly = true)
	public String getLoginToken(LoginRequest loginRequest) {
		Member member = findByMemberId(loginRequest.memberId());
		if (!member.isValidPassword(loginRequest.password()))
			throw new MemberNotFoundException("비밀번호가 일치하지 않습니다");
		return jwtTokenProvider.createJwtAccessToken(member.getMemberDescription().getMemberId());
	}

	private Member findByMemberId(String memberId) {
		return memberRepository.findByMemberId(memberId)
			.orElseThrow(() -> new MemberNotFoundException("id가 일치하지 않습니다"));
	}

}