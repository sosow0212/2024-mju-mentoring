package com.mju.mentoring.exam.board.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.mentoring.exam.board.controller.provider.JwtTokenProvider;
import com.mju.mentoring.exam.board.domain.Member;
import com.mju.mentoring.exam.board.domain.MemberRepository;
import com.mju.mentoring.exam.board.exception.NoMemberException;
import com.mju.mentoring.exam.board.service.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Transactional(readOnly = true)
    public String getLoginToken(LoginRequest loginRequest) {
        Member member = findByMemberId(loginRequest.memberId());
        if(!member.validation(loginRequest.password()))
            throw new NoMemberException("비밀번호가 일치하지 않습니다");
        return jwtTokenProvider.createJwtAccessToken(member.getMemberDescription().getMemberId());
    }

    private Member findByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId).orElseThrow(() -> new NoMemberException("id가 일치하지 않습니다"));
    }


}
