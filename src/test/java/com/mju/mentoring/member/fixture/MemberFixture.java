package com.mju.mentoring.member.fixture;

import com.mju.mentoring.member.domain.AuthInformation;
import com.mju.mentoring.member.domain.Member;

public class MemberFixture {

    private static final Long DEFAULT_ID = 1L;

    public static Member id_없는_멤버_생성() {
        return Member.of("id", "password", "nickname");
    }

    public static Member 멤버_생성() {
        return Member.builder()
            .id(DEFAULT_ID)
            .authInformation(AuthInformation.of("id", "password"))
            .nickname("nickname")
            .build();
    }
}
