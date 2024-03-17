package com.mju.mentoring.exam.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mju.mentoring.exam.board.component.JwtTokenProvider;
import com.mju.mentoring.exam.board.domain.Member;
import com.mju.mentoring.exam.board.domain.MemberDescription;
import com.mju.mentoring.exam.board.domain.MemberRepository;
import com.mju.mentoring.exam.board.exception.MemberNotFoundException;
import com.mju.mentoring.exam.board.service.dto.LoginRequest;
import com.mju.mentoring.exam.board.service.dto.SignUpRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	private final JwtTokenProvider jwtTokenProvider;

	@Transactional(readOnly = true)
	public String getLoginToken(LoginRequest loginRequest) {
		Member member = findByMemberId(loginRequest.loginId());
		return jwtTokenProvider.createJwtAccessToken(member);
	}

	private Member findByMemberId(String loginId) {
		return memberRepository.findByLoginId(loginId)
			.orElseThrow(() -> new MemberNotFoundException("id가 일치하지 않습니다"));
	}

	public void save(SignUpRequest signUpRequest) {
		Member member = new Member(
			new MemberDescription(signUpRequest.loginId(), signUpRequest.username(), signUpRequest.password(),
				signUpRequest.nickname()));
		this.memberRepository.save(member);
	}
}
