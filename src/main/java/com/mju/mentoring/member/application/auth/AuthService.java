package com.mju.mentoring.member.application.auth;

import com.mju.mentoring.member.application.auth.dto.SignInRequest;
import com.mju.mentoring.member.application.auth.dto.SignupRequest;
import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.domain.MemberRepository;
import com.mju.mentoring.member.domain.TokenManager;
import com.mju.mentoring.member.exception.exceptions.DuplicateNicknameException;
import com.mju.mentoring.member.exception.exceptions.DuplicateUsernameException;
import com.mju.mentoring.member.exception.exceptions.MemberNotFoundException;
import com.mju.mentoring.member.exception.exceptions.WrongPasswordException;
import com.mju.mentoring.member.ui.auth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenManager<Long> tokenManager;

    @Transactional
    public void signup(SignupRequest request) {
        checkDuplicate(request.username(), request.nickname());
        Member member = Member.of(request.username(), request.password(), request.nickname());
        memberRepository.save(member);
    }

    public TokenResponse signIn(final SignInRequest request) {
        String username = request.username();
        Member member = findMemberByUsername(username);

        if (!member.isValidPassword(request.password())) {
            throw new WrongPasswordException();
        }

        String accessToken = tokenManager.create(member.getId());
        return new TokenResponse(accessToken);
    }

    private Member findMemberByUsername(final String username) {
        return memberRepository.findByUsername(username)
            .orElseThrow(MemberNotFoundException::new);
    }

    private void checkDuplicate(final String username, final String nickname) {
        if (memberRepository.existsByUsername(username)) {
            throw new DuplicateUsernameException();
        }

        if (memberRepository.existsByNickname(nickname)) {
            throw new DuplicateNicknameException();
        }
    }
}
