package com.mju.mentoring.board.service;


import com.mju.mentoring.board.infrastructure.BoardFakeRepository;
import com.mju.mentoring.exam.board.domain.Board;
import com.mju.mentoring.exam.board.domain.BoardRepository;
import com.mju.mentoring.exam.board.exception.BoardNotFoundException;
import com.mju.mentoring.exam.board.service.BoardService;
import com.mju.mentoring.exam.board.service.dto.BoardCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.mju.mentoring.board.fixture.BoardFixture.게시글_생성_id없음;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class BoardServiceTest {

    // 1. stub 가짜 객체를 사용해서 단위테스트 수행 (실제 빈 띄우는 것이 아님)

    private BoardService boardService;
    private BoardRepository boardRepository;

    @BeforeEach
    void setup() {
        boardRepository = new BoardFakeRepository();
        boardService = new BoardService(boardRepository);
    }

    @Test
    void 게시글을_저장한다() {
        // given
        BoardCreateRequest req = new BoardCreateRequest("title", "content");

        // when
        Long savedBoard = boardService.save(req);

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
