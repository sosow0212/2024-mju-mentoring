package com.mju.mentoring.board.application;

import static com.mju.mentoring.board.fixture.BoardFixture.id_없는_게시글_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.mju.mentoring.board.application.dto.BoardCreateRequest;
import com.mju.mentoring.board.application.dto.BoardDeleteRequest;
import com.mju.mentoring.board.application.dto.BoardUpdateRequest;
import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.domain.BoardRepository;
import com.mju.mentoring.board.exception.exceptions.BoardNotFoundException;
import com.mju.mentoring.board.infrastructure.BoardFakeRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class BoardServiceTest {

    private static final Long DEFAULT_WRITER_ID = 1L;

    private BoardService boardService;
    private BoardRepository boardRepository;

    @BeforeEach
    void setup() {
        boardRepository = new BoardFakeRepository();
        boardService = new BoardService(boardRepository);
    }

    @Test
    void 게시물_저장() {
        // given
        BoardCreateRequest request = new BoardCreateRequest("title", "content");

        // when
        Long savedId = boardService.save(DEFAULT_WRITER_ID, request);
        List<Board> findBoards = boardService.findAll();

        // then
        assertSoftly(softly -> {
            softly.assertThat(findBoards)
                .hasSize(1);

            softly.assertThat(savedId)
                .isEqualTo(1L);
        });
    }

    @Test
    void 게시글_모두_찾기() {
        // given
        Board board = id_없는_게시글_생성();
        boardRepository.save(board);

        // when
        List<Board> findBoards = boardService.findAll();

        // then
        assertSoftly(softly -> {
            softly.assertThat(findBoards.size())
                .isEqualTo(1);

            softly.assertThat(findBoards.get(0))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(board);
        });
    }

    @Test
    void id로_게시글_조회() {
        // given
        Board board = id_없는_게시글_생성();
        Board savedBoard = boardRepository.save(board);

        // when
        Board findBoard = boardService.findById(savedBoard.getId());

        // then
        assertThat(findBoard).isEqualTo(savedBoard);
    }

    @Test
    void id로_조회한_게시글이_없을_시_예외처리() {
        // when & then
        assertThatThrownBy(() -> boardService.findById(0L))
            .isInstanceOf(BoardNotFoundException.class);
    }

    @Test
    void 게시물_업데이트() {
        // given
        String originTitle = "origin title";
        String originContent = "origin content";
        BoardCreateRequest createRequest = new BoardCreateRequest(originTitle, originContent);

        String updateTitle = "update title";
        String updateContent = "update content";
        BoardUpdateRequest updateRequest = new BoardUpdateRequest(updateTitle, updateContent);

        // when
        Long savedId = boardService.save(DEFAULT_WRITER_ID, createRequest);
        boardService.update(DEFAULT_WRITER_ID, savedId, updateRequest);

        // then
        assertSoftly(softly -> {
            softly.assertThat(updateTitle).isEqualTo(updateTitle);
            softly.assertThat(updateContent).isEqualTo(updateContent);
        });
    }

    @Test
    void 없는_게시물을_업데이트_시_예외처리() {
        // given
        String updateTitle = "update title";
        String updateContent = "update content";
        BoardUpdateRequest updateRequest = new BoardUpdateRequest(updateTitle, updateContent);

        // when & then
        assertThatThrownBy(() -> boardService.update(DEFAULT_WRITER_ID, 0L, updateRequest))
            .isInstanceOf(BoardNotFoundException.class);
    }

    @Test
    void 게시물_삭제() {
        // given
        BoardCreateRequest request = new BoardCreateRequest("title", "content");

        // when
        Long savedId = boardService.save(DEFAULT_WRITER_ID, request);
        boardService.deleteById(DEFAULT_WRITER_ID, savedId);

        // then
        List<Board> boards = boardService.findAll();
        assertThat(boards).isEmpty();
    }

    @Test
    void 없는_게시물_삭제_시_에외처리() {
        // when & then
        assertThatThrownBy(() -> boardService.deleteById(DEFAULT_WRITER_ID, 0L))
            .isInstanceOf(BoardNotFoundException.class);
    }

    @Test
    void id로_여러_게시물_한번에_삭제() {
        // given
        BoardCreateRequest request1 = new BoardCreateRequest("title1", "content1");
        BoardCreateRequest request2 = new BoardCreateRequest("title2", "content2");

        Long savedId1 = boardService.save(DEFAULT_WRITER_ID, request1);
        Long savedId2 = boardService.save(DEFAULT_WRITER_ID, request2);

        // when
        boardService.deleteAllById(new BoardDeleteRequest(List.of(savedId1, savedId2)));

        // then
        List<Board> boards = boardService.findAll();
        assertThat(boards).isEmpty();
    }
}
