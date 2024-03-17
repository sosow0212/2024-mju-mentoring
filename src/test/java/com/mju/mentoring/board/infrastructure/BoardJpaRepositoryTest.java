package com.mju.mentoring.board.infrastructure;

import static com.mju.mentoring.board.fixture.BoardFixtures.게시글_id_없음;

import static com.mju.mentoring.member.fixture.MemberFixtures.회원_id_없음;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.global.support.CleanDatabase;

import com.mju.mentoring.member.infrastructure.memberrepository.MemberJpaRepository;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
@CleanDatabase
public class BoardJpaRepositoryTest {

    @Autowired
    private BoardJpaRepository boardJpaRepository;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @BeforeEach
    void init() {
        memberJpaRepository.save(회원_id_없음());
    }

    @Test
    void 게시글을_저장한다() {
        // given
        Board newBoard = 게시글_id_없음();

        // when
        boardJpaRepository.save(newBoard);

        // then
        assertThat(newBoard.getId()).isEqualTo(1L);
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
    void 여러_게시글을_조회한다() {
        // given
        Board boardOne = 게시글_id_없음();
        Board boardTwo = 게시글_id_없음();

        boardJpaRepository.save(boardOne);
        boardJpaRepository.save(boardTwo);

        // when
        List<Board> boards = boardJpaRepository.findAll();

        // then
        assertSoftly((softly) -> {
            softly.assertThat(boards).hasSize(2);
            softly.assertThat(boards.contains(boardOne)).isTrue();
            softly.assertThat(boards.contains(boardTwo)).isTrue();
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
        assertThat(boardJpaRepository.findById(targetBoard.getId())).isEmpty();
    }
}
