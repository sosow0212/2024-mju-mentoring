package com.mju.mentoring.member.fixture;

import com.mju.mentoring.member.domain.AuthInformation;
import com.mju.mentoring.member.domain.Member;

public class MemberFixture {

    private static final String MEMBER_DEFAULT_USERNAME = "id";
    private static final String MEMBER_DEFAULT_NICKNAME = "nickname";
    private static final String MEMBER_DEFAULT_PASSWORD = "password";
    private static final Long DEFAULT_ID = 1L;

    public static Member id_없는_멤버_생성() {
        return Member.of(MEMBER_DEFAULT_USERNAME, MEMBER_DEFAULT_PASSWORD, MEMBER_DEFAULT_NICKNAME);
    }

    public static Member 멤버_생성() {
        return Member.builder()
            .id(DEFAULT_ID)
            .authInformation(AuthInformation.of(MEMBER_DEFAULT_USERNAME, MEMBER_DEFAULT_PASSWORD))
            .nickname(MEMBER_DEFAULT_NICKNAME)
            .build();
    }
}
