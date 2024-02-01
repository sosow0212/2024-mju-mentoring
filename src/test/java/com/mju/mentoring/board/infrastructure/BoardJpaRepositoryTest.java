package com.mju.mentoring.board.infrastructure;

import static com.mju.mentoring.board.fixture.BoardFixture.id_없는_게시글_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.global.DatabaseCleaner;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
@DatabaseCleaner
class BoardJpaRepositoryTest {

    @Autowired
    private BoardJpaRepository boardJpaRepository;

    @Test
    void 게시글_저장_테스트() {
        // given
        Board board = id_없는_게시글_생성();

        // when
        Board savedBoard = boardJpaRepository.save(board);

        // then
        assertSoftly(softly -> {
                softly.assertThat(savedBoard)
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(board);

                softly.assertThat(savedBoard.getId())
                    .isEqualTo(1L);
            }
        );
    }

    @Test
    void 게시글_조회_테스트() {
        // given
        Board board = id_없는_게시글_생성();
        Board savedBoard = boardJpaRepository.save(board);

        // when
        Optional<Board> findBoard = boardJpaRepository.findById(savedBoard.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(findBoard).isPresent();
            softly.assertThat(findBoard.get())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(savedBoard);
        });
    }

    @Test
    void 게시글_모두_조회_테스트() {
        // given
        Board board1 = id_없는_게시글_생성();
        boardJpaRepository.save(board1);

        Board board2 = id_없는_게시글_생성();
        boardJpaRepository.save(board2);

        // when
        List<Board> findBoards = boardJpaRepository.findAll();

        // then
        assertThat(findBoards.size()).isEqualTo(2);
    }

    @Test
    void 게시글_삭제_테스트() {
        // given
        Board board = id_없는_게시글_생성();
        Board savedBoard = boardJpaRepository.save(board);

        // when
        boardJpaRepository.delete(savedBoard);

        // then
        List<Board> findBoards = boardJpaRepository.findAll();
        assertThat(findBoards).isEmpty();
    }
}
