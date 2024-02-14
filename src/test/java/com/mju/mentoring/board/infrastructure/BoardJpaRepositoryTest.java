package com.mju.mentoring.board.infrastructure;

import static com.mju.mentoring.board.fixture.BoardFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mju.mentoring.exam.board.domain.Board;
import com.mju.mentoring.exam.board.infrastructure.BoardJpaRepository;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class BoardJpaRepositoryTest {

	@Autowired
	private BoardJpaRepository boardJpaRepository;

	@Test
	void 게시글을_저장한다() {
		// given
		Board board = 게시글_생성();

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
		Board board = 게시글_생성_id없음();
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
		Board board = 게시글_생성_id없음();
		for (int i = 0; i < 3; i++) {
			board = 게시글_생성_id없음();
			Board savedBoard = boardJpaRepository.save(board);
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
		Board board = 게시글_생성_id없음();
		boardJpaRepository.delete(board);

		// when
		List<Board> foundBoards = boardJpaRepository.findAll();

		//then
		assertThat(foundBoards).hasSize(0);
	}
}
