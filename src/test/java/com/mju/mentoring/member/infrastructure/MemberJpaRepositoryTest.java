package com.mju.mentoring.member.infrastructure;

import static com.mju.mentoring.member.fixture.MemberFixture.id_없는_멤버_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.mju.mentoring.global.DatabaseCleaner;
import com.mju.mentoring.member.domain.Member;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
@DatabaseCleaner
class MemberJpaRepositoryTest {

    private static final Long MEMBER_DEFAULT_ID = 1L;
    private static final String MEMBER_DEFAULT_USERNAME = "id";
    private static final String MEMBER_DEFAULT_NICKNAME = "nickname";

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Test
    void 멤버_저장() {
        // given
        Member member = id_없는_멤버_생성();

        // when
        memberJpaRepository.save(member);
        Member findMember = memberJpaRepository.findById(MEMBER_DEFAULT_ID)
            .get();

        // then
        assertThat(member)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(findMember);
    }

    @Test
    void 아이디로_멤버_탐색() {
        // given
        Member member = id_없는_멤버_생성();

        // when
        memberJpaRepository.save(member);
        boolean result = memberJpaRepository.existsByAuthInformationUsername(
            MEMBER_DEFAULT_USERNAME);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 닉네임으로_멤버_탐색() {
        // given
        Member member = id_없는_멤버_생성();

        // when
        memberJpaRepository.save(member);
        boolean result = memberJpaRepository.existsByNickname(MEMBER_DEFAULT_NICKNAME);

        // then
        assertThat(result).isTrue();
    }
}
