package com.mju.mentoring.member.application.auth;

import com.mju.mentoring.member.application.auth.dto.SignupRequest;
import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.domain.MemberRepository;
import com.mju.mentoring.member.exception.exceptions.DuplicateNicknameException;
import com.mju.mentoring.member.exception.exceptions.DuplicateUsernameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public void signup(SignupRequest request) {
        checkDuplicate(request.username(), request.nickname());
        Member member = Member.of(request.username(), request.nickname(), request.password());
        memberRepository.save(member);
    }

    private void checkDuplicate(final String username, final String nickname) {
        if (memberRepository.isExistByUsername(username)) {
            throw new DuplicateUsernameException();
        }

        if (memberRepository.isExistByNickname(nickname)) {
            throw new DuplicateNicknameException();
        }
    }
}
