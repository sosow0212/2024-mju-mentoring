package com.mju.mentoring.board.service;

import static com.mju.mentoring.board.fixture.BoardFixture.*;
import static com.mju.mentoring.board.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import com.mju.mentoring.board.infrastructure.BoardFakeRepository;
import com.mju.mentoring.board.infrastructure.MemberFakeRepository;
import com.mju.mentoring.exam.board.domain.Board;
import com.mju.mentoring.exam.board.domain.BoardRepository;
import com.mju.mentoring.exam.board.domain.Member;
import com.mju.mentoring.exam.board.domain.MemberRepository;
import com.mju.mentoring.exam.board.exception.BoardNotFoundException;
import com.mju.mentoring.exam.board.service.BoardService;
import com.mju.mentoring.exam.board.service.dto.BoardCreateRequest;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class BoardServiceTest {

	// 1. stub 가짜 객체를 사용해서 단위테스트 수행 (실제 빈 띄우는 것이 아님)

	private BoardService boardService;

	private BoardRepository boardRepository;
	private MemberRepository memberRepository;

	@BeforeEach
	void setup() {
		boardRepository = new BoardFakeRepository();
		memberRepository = new MemberFakeRepository();
		boardService = new BoardService(boardRepository, memberRepository);
	}

	@Test
	void 게시글을_저장한다() {
		// given
		BoardCreateRequest req = new BoardCreateRequest("title", "content");
		Member member = 멤버_생성();
		memberRepository.save(member);

		// when
		Long savedBoard = boardService.save(1L, req);

		// then
		assertThat(savedBoard).isEqualTo(1L);
	}

	@Test
	void 로그인한_회원의_게시글을_저장한다() {
		// given
		Member member = 멤버_생성();
		memberRepository.save(member);
		BoardCreateRequest req = new BoardCreateRequest("title", "content");

		// when
		Long savedBoard = boardService.save(member.getId(), req);

		// then
		assertThat(savedBoard).isEqualTo(1L);
	}

	@Test
	void 게시글을_모두_찾는다() {
		// given
		Board board = 게시글_생성_id없음();
		boardRepository.save(board);

		// when
		List<Board> result = boardService.findAll();

		// then
		assertSoftly(softly -> {
			softly.assertThat(result).hasSize(1);
			softly.assertThat(result.get(0).getId()).isEqualTo(1L);
		});
	}

	@Test
	void 게시글을_id로_조회한다() {
		// given
		Board board = 게시글_생성_id없음();
		Board savedBoard = boardRepository.save(board);

		// when
		Optional<Board> found = boardRepository.findById(savedBoard.getId());

		// then
		assertSoftly(softly -> {
			softly.assertThat(found).isPresent();
			softly.assertThat(found.get())
				.usingRecursiveComparison()
				.isEqualTo(savedBoard);
		});
	}

	@Test
	void 게시글이_존재하지_않으면_예외를_발생한다() {
		// when & then
		assertThatThrownBy(() -> boardService.findById(-1L))
			.isInstanceOf(BoardNotFoundException.class);
	}
}
