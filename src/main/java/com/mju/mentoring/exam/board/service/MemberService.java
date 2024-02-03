package com.mju.mentoring.exam.board.service;

import com.mju.mentoring.exam.board.domain.Member;
import com.mju.mentoring.exam.board.domain.MemberRepository;
import com.mju.mentoring.exam.board.exception.NoMemberException;
import com.mju.mentoring.exam.board.provider.JwtTokenProvider;
import com.mju.mentoring.exam.board.service.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;
    @Transactional(readOnly = true)
    public String getLoginToken(LoginRequest loginRequest) {
        Member member = findByMemberId(loginRequest.memberId());
        if(!member.validation(loginRequest.password()))
            throw new NoMemberException("비밀번호가 일치하지 않습니다");
        return jwtTokenProvider.createJwtAccessToken(member.getMemberDescription().getMemberId());
    }

    private Member findByMemberId(String memberId) {
        System.out.println(memberRepository.findByMemberId(memberId));
        return memberRepository.findByMemberId(memberId).orElseThrow(() -> new NoMemberException("id가 일치하지 않습니다"));
    }


}
