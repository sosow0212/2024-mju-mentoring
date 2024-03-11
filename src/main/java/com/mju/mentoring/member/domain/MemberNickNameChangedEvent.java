package com.mju.mentoring.member.domain;

import com.mju.mentoring.global.event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberNickNameChangedEvent extends Event {

    private final Long memberId;
    private final String newNickname;
}
