package com.mju.mentoring.board.infrastructure;

import static com.mju.mentoring.board.fixture.MemberFixture.멤버_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mju.mentoring.exam.board.domain.Member;
import com.mju.mentoring.exam.board.infrastructure.MemberJpaRepository;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class MemberJpaRepositoryTest {

	@Autowired
	private MemberJpaRepository memberJpaRepository;

	@Test
	void 게시글을_저장한다() {
		// given
		Member member = 멤버_생성();

		// when
		Member savedMember = memberJpaRepository.save(member);

		// then
		assertSoftly(softly -> {
			softly.assertThat(savedMember);
			softly.assertThat(savedMember)
				.usingRecursiveComparison()
				.ignoringFields("id")
				.isEqualTo(member);
		});
	}

	@Test
	void 게시글을_id로_조회한다() {
		// given
		Member member = 멤버_생성();
		Member savedMember = memberJpaRepository.save(member);

		// when
		Optional<Member> foundBoard = memberJpaRepository.findById(member.getId());

		// then
		assertSoftly(softly -> {
			softly.assertThat(foundBoard).isNotEmpty();
			softly.assertThat(foundBoard).isPresent();
			softly.assertThat(foundBoard.get())
				.usingRecursiveComparison()
				.ignoringFields("id")
				.isEqualTo(member);
		});
	}

	@Test
	void 게시글을_memberId로_조회한다() {
		// given
		Member member = 멤버_생성();
		Member savedMember = memberJpaRepository.save(member);

		// when
		Optional<Member> foundBoard = memberJpaRepository.findMemberByMemberDescriptionLoginId(
			member.getMemberDescription().getLoginId());

		// then
		assertThat(foundBoard).isNotEmpty();
		assertSoftly(softly -> {
			softly.assertThat(foundBoard).isPresent();
			softly.assertThat(foundBoard.get())
				.usingRecursiveComparison()
				.ignoringFields("id")
				.isEqualTo(member);
		});
	}

	@Test
	void 게시글을_삭제한다() {
		//given
		Member member = 멤버_생성();
		memberJpaRepository.delete(member);

		// when
		List<Member> foundBoards = memberJpaRepository.findAll();

		//then
		assertThat(foundBoards).hasSize(0);
	}
}
