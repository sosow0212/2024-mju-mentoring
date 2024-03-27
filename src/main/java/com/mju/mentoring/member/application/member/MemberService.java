package com.mju.mentoring.member.application.member;

import com.mju.mentoring.global.event.Events;
import com.mju.mentoring.member.application.member.dto.ChangeNickNameRequest;
import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.domain.MemberNickNameChangedEvent;
import com.mju.mentoring.member.domain.MemberRepository;
import com.mju.mentoring.member.exception.exceptions.DuplicateNicknameException;
import com.mju.mentoring.member.exception.exceptions.DuplicateUsernameException;
import com.mju.mentoring.member.exception.exceptions.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void changeNickName(final Long memberId, final ChangeNickNameRequest request) {
        Member member = findMemberById(memberId);
        String newNickname = request.newNickname();
        member.changeNickName(newNickname);
        Events.raise(new MemberNickNameChangedEvent(memberId, newNickname));
    }

    public Member findMemberById(final Long id) {
        return memberRepository.findById(id)
            .orElseThrow(MemberNotFoundException::new);
    }

    public Member findMemberByUsername(final String username) {
        return memberRepository.findByUsername(username)
            .orElseThrow(MemberNotFoundException::new);
    }

    public void checkDuplicate(final String username, final String nickname) {
        if (memberRepository.existsByUsername(username)) {
            throw new DuplicateUsernameException();
        }

        if (memberRepository.existsByNickname(nickname)) {
            throw new DuplicateNicknameException();
        }
    }
}
