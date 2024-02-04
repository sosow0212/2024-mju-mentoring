package com.mju.mentoring.member.service;

import static com.mju.mentoring.member.service.helper.MemberServiceHelper.findMemberByAuth;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.domain.MemberAuth;
import com.mju.mentoring.member.domain.MemberRepository;
import com.mju.mentoring.member.domain.PasswordManager;
import com.mju.mentoring.member.domain.JwtManager;
import com.mju.mentoring.member.service.dto.AuthRequest;
import com.mju.mentoring.member.service.dto.LoginRequest;
import com.mju.mentoring.member.service.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private final PasswordManager passwordManager;
    private final JwtManager jwtManager;
    private final MemberRepository memberRepository;

    @Transactional
    public Member signup(final SignupRequest request) {
        String nickname = request.nickname();
        String username = request.username();
        String password = passwordManager.encode(request.password());

        MemberAuth memberAuth = new MemberAuth(username, password);
        Member member = new Member(nickname, memberAuth);
        return memberRepository.save(member);
    }

    public Member login(final LoginRequest request) {
        String encodedPassword = passwordManager.encode(request.password());
        AuthRequest authRequest = new AuthRequest(request.username(), encodedPassword);
        return findMemberByAuth(memberRepository, authRequest);
    }

    public String jwtLogin(final LoginRequest request) {
        String encodedPassword = passwordManager.encode(request.password());
        AuthRequest authRequest = new AuthRequest(request.username(), encodedPassword);
        Member loginMember = findMemberByAuth(memberRepository, authRequest);
        return jwtManager.generateToken(loginMember.getUsername());
    }
}
