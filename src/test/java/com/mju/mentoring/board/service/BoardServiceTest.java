package com.mju.mentoring.board.service;

import static com.mju.mentoring.board.fixture.BoardFixtures.게시글_id_있음;
import static com.mju.mentoring.member.fixture.MemberFixtures.회원_id_있음;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.domain.BoardRepository;
import com.mju.mentoring.board.exception.exceptions.BoardNotFoundException;
import com.mju.mentoring.board.infrastructure.BoardTestRepository;
import com.mju.mentoring.board.service.dto.BoardCreateRequest;
import com.mju.mentoring.board.service.dto.BoardTextUpdateRequest;
import com.mju.mentoring.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import java.util.List;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class BoardServiceTest {

    private BoardService boardService;
    private BoardRepository boardRepository;

    @BeforeEach
    void init() {
        boardRepository = new BoardTestRepository();
        boardService = new BoardService(boardRepository);
    }

    @Test
    void 게시글을_저장한다() {
        // given
        String title = "title for save";
        String content = "content for save";
        Member member = 회원_id_있음();
        BoardCreateRequest request = new BoardCreateRequest(title, content);

        // when
        boardService.save(request, member);

        // then
        System.out.println(boardService.findAll());
        assertThat(boardService.findAll()).size().isEqualTo(1);
    }

    @Test
    void 게시글을_조회한다() {
        // given
        String title = "title for search";
        String content = "content for search";
        Member member = 회원_id_있음();
        BoardCreateRequest request = new BoardCreateRequest(title, content);
        boardService.save(request, member);

        // when
        Board findBoard = boardService.searchById(1L);

        // then
        assertSoftly((softly) -> {
            softly.assertThat(findBoard.getTitle()).isEqualTo(title);
            softly.assertThat(findBoard.getContent()).isEqualTo(content);
        });
    }

    @Test
    void 모든_게시글을_조회한다() {
        // given
        String titleOne = "title one";
        String titleTwo = "title two";
        String contentOne = "content one";
        String contentTwo = "content two";

        Member member = 회원_id_있음();
        BoardCreateRequest requestOne = new BoardCreateRequest(titleOne, contentOne);
        BoardCreateRequest requestTwo = new BoardCreateRequest(titleTwo, contentTwo);

        boardService.save(requestOne, member);
        boardService.save(requestTwo, member);

        // when
        List<Board> boards = boardService.findAll();
        Board saveOne = boards.get(0);
        Board saveTwo = boards.get(1);

        // then
        assertSoftly((softly) -> {
            softly.assertThat(boards.size()).isEqualTo(2);
            softly.assertThat(saveOne.getTitle()).isEqualTo(titleOne);
            softly.assertThat(saveTwo.getTitle()).isEqualTo(titleTwo);
        });
    }

    @Test
    void 게시글을_수정한다() {
        // given
        String updatedTitle = "title for update (edited)";
        String updatedContent = "content for update (edited)";
        BoardTextUpdateRequest updateRequest = new BoardTextUpdateRequest(updatedTitle, updatedContent);

        String title = "title for update (origin)";
        String content = "content for update (origin)";
        Member member = 회원_id_있음();
        BoardCreateRequest createRequest = new BoardCreateRequest(title, content);

        boardService.save(createRequest, member);
        Board targetBoard = boardService.searchById(1L);

        // when
        boardService.updateText(targetBoard.getId(), updateRequest);

        // then
        assertSoftly((softly) -> {
            softly.assertThat(targetBoard.getTitle()).isEqualTo(updatedTitle);
            softly.assertThat(targetBoard.getContent()).isEqualTo(updatedContent);
        });
    }

    @Test
    void 게시글을_삭제한다() {
        // given
        String title = "title for delete";
        String content = "content for delete";
        Member member = 회원_id_있음();
        BoardCreateRequest request = new BoardCreateRequest(title, content);
        boardService.save(request, member);
        Board findBoard = boardService.searchById(1L);

        // when
        boardRepository.delete(findBoard);

        // then
        assertSoftly((softly) -> {
            softly.assertThat(boardService.findAll()).doesNotContain(findBoard);
            softly.assertThatThrownBy(() -> boardService.searchById(findBoard.getId()))
                    .isInstanceOf(BoardNotFoundException.class);
        });
    }

    @Test
    void 없는_게시글을_수정할_시_예외가_발생한다() {
        // given
        String updatedTitle = "title for update (edited)";
        String updatedContent = "content for update (edited)";
        BoardTextUpdateRequest updateRequest = new BoardTextUpdateRequest(updatedTitle, updatedContent);
        Long boardId = 100L;

        // when & then
        assertThatThrownBy(() -> boardService.updateText(boardId, updateRequest))
                .isInstanceOf(BoardNotFoundException.class);
    }

    @Test
    void 없는_게시글을_삭제할_시_예외가_발생한다() {
        // given
        Board notSavedBoard = 게시글_id_있음();

        // when & then
        assertThatThrownBy(() -> boardService.deleteById(notSavedBoard.getId()))
                .isInstanceOf(BoardNotFoundException.class);
    }

    @Test
    void 없는_게시글을_조회할_시_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> boardService.searchById(100L))
                .isInstanceOf(BoardNotFoundException.class);
    }
}
