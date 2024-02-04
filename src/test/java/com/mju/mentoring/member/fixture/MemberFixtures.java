package com.mju.mentoring.member.fixture;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.domain.MemberAuth;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class MemberFixtures {

    private static final String ENCRYPTED_PASSWORD = "passwordencrypt";

    public static Member 회원_id_있음() {
        return Member.builder()
                .id(1L)
                .nickname("nickname")
                .memberAuth(new MemberAuth("username", ENCRYPTED_PASSWORD))
                .build();
    }

    public static Member 회원_id_없음() {
        return new Member("nickname", new MemberAuth("username", ENCRYPTED_PASSWORD));
    }
}
