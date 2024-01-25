package com.mju.mentoring.board.infrastructure;

import static com.mju.mentoring.board.fixture.BoardFixtures.게시글_id_없음;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.mju.mentoring.board.domain.Board;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
public class BoardJpaRepositoryTest {

    @Autowired
    private BoardJpaRepository boardJpaRepository;

    @Test
    void 게시글을_저장한다() {
        // given
        Board newBoard = 게시글_id_없음();

        // when
        boardJpaRepository.save(newBoard);

        // then
        assertThat(newBoard.getId()).isNotNull();
    }

    @Test
    void 게시글을_조회한다() {
        // given
        Board targetBoard = 게시글_id_없음();
        boardJpaRepository.save(targetBoard);

        // when
        Optional<Board> findBoard = boardJpaRepository.findById(targetBoard.getId());

        // then
        assertSoftly((softly) -> {
            softly.assertThat(findBoard.isPresent()).isTrue();
            softly.assertThat(findBoard.get())
                    .usingRecursiveComparison()
                    .isEqualTo(targetBoard);
        });
    }

    @Test
    void 게시글을_삭제한다() {
        // given
        Board targetBoard = 게시글_id_없음();
        boardJpaRepository.save(targetBoard);

        // when
        boardJpaRepository.delete(targetBoard);

        // then
        assertThat(boardJpaRepository.findById(targetBoard.getId()).isEmpty()).isTrue();
    }
}
