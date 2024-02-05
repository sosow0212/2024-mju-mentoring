package com.mju.mentoring.member.infrastructure;

import static com.mju.mentoring.member.fixture.MemberFixtures.회원_id_없음;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.mju.mentoring.global.support.CleanDatabase;
import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.infrastructure.memberrepository.MemberJpaRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.Optional;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
@CleanDatabase
public class MemberJpaRepositoryTest {

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Test
    void 회원_생성() {
        // given
        Member member = 회원_id_없음();

        // when
        Member saveMember = memberJpaRepository.save(member);

        // then
        assertThat(saveMember).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(member);
    }

    @Test
    void 닉네임으로_회원_조회() {
        // given
        Member member = 회원_id_없음();
        memberJpaRepository.save(member);

        // when
        Optional<Member> findMember = memberJpaRepository.findByNickname(member.getNickname());

        // then
        assertSoftly(softly -> {
            softly.assertThat(findMember).isNotEmpty();
            softly.assertThat(findMember.get()).usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(member);
        });
    }

    @Test
    void 회원_삭제() {
        // given
        Member member = 회원_id_없음();
        Member saveMember = memberJpaRepository.save(member);

        // when
        memberJpaRepository.deleteById(saveMember.getId());
        Optional<Member> result = memberJpaRepository.findByNickname(saveMember.getNickname());

        // then
        assertThat(result).isEmpty();
    }

    @Nested
    class 예외_테스트 {

        @Test
        void 닉네임_중복_예외() {
            // given
            Member member = 회원_id_없음();
            memberJpaRepository.save(member);
            Member secondMember = 회원_id_없음();

            // when
            assertThatThrownBy(() -> memberJpaRepository.save(secondMember))
                    .isInstanceOf(DataIntegrityViolationException.class);
        }

        @Test
        void 없는_닉네임_조회_예외() {
            // given
            Member member = 회원_id_없음();
            memberJpaRepository.save(member);

            // when
            Optional<Member> result = memberJpaRepository.findByNickname("other");

            // then
            assertThat(result).isEmpty();
        }
    }
}
