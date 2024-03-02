package com.mju.mentoring.board.infrastructure;

import static com.mju.mentoring.board.fixture.BoardFixture.게시글_생성_id없음;
import static com.mju.mentoring.board.fixture.MemberFixture.멤버_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mju.mentoring.exam.board.domain.Board;
import com.mju.mentoring.exam.board.domain.Member;
import com.mju.mentoring.exam.board.infrastructure.BoardJpaRepository;
import com.mju.mentoring.exam.board.infrastructure.MemberJpaRepository;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class BoardJpaRepositoryTest {

	@Autowired
	private BoardJpaRepository boardJpaRepository;
	@Autowired
	private MemberJpaRepository memberJpaRepository;
	private Member member;

	@BeforeEach
	void setUp() {
		member = 멤버_생성();
		memberJpaRepository.save(member);
	}

	@Test
	void 게시글을_저장한다() {
		// given
		Board board = 게시글_생성_id없음(member);

		// when
		Board savedBoard = boardJpaRepository.save(board);

		// then

		assertSoftly(softly -> {
			softly.assertThat(savedBoard);
			softly.assertThat(savedBoard)
				.usingRecursiveComparison()
				.ignoringFields("id")
				.isEqualTo(board);
		});
	}

	@Test
	void 게시글을_id로_조회한다() {
		// given
		Board board = 게시글_생성_id없음(member);

		Board savedBoard = boardJpaRepository.save(board);

		// when
		Optional<Board> foundBoard = boardJpaRepository.findById(board.getId());

		// then
		assertSoftly(softly -> {
			softly.assertThat(foundBoard).isPresent();
			softly.assertThat(foundBoard.get())
				.usingRecursiveComparison()
				.ignoringFields("id")
				.isEqualTo(board);
		});
	}

	@Test
	void 게시글을_전부_조회한다() {
		// given
		for (int i = 0; i < 3; i++) {
			Board board = 게시글_생성_id없음(member);
			boardJpaRepository.save(board);
		}

		// when
		List<Board> foundBoards = boardJpaRepository.findAll();

		// then
		assertSoftly(softly -> {
			softly.assertThat(foundBoards).size().isEqualTo(3);
			for (int i = 0; i < 3; i++)
				softly.assertThat(foundBoards.get(i))
					.usingRecursiveComparison()
					.ignoringFields("id");
		});
	}

	@Test
	void 게시글을_삭제한다() {
		//given
		Board board = 게시글_생성_id없음(member);
		boardJpaRepository.save(board);
		boardJpaRepository.delete(board);

		// when
		List<Board> foundBoards = boardJpaRepository.findAll();

		//then
		assertThat(foundBoards).hasSize(0);
	}
}
