package com.mju.mentoring.member.domain;

import static com.mju.mentoring.member.fixture.MemberFixture.id_없는_멤버_생성;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    void 포인트_증가_테스트() {
        // given
        Member member = id_없는_멤버_생성();

        // when
        member.increasePoint(1000L);

        // then
        assertThat(member.getPoint().getPoint()).isEqualTo(1000L);
    }

}
