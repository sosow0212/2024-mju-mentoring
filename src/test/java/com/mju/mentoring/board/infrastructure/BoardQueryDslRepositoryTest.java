package com.mju.mentoring.board.infrastructure;

import static com.mju.mentoring.board.fixture.BoardFixture.id_없는_게시글_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.global.DatabaseCleaner;
import com.mju.mentoring.global.config.TestQuerydslConfig;
import java.util.List;
import org.apache.commons.lang3.stream.IntStreams;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
@DatabaseCleaner
@Import(TestQuerydslConfig.class)
class BoardQueryDslRepositoryTest {

    private static final int BOARD_COUNT = 3;

    @Autowired
    private BoardQueryDslRepository boardQueryDslRepository;
    @Autowired
    private BoardJpaRepository boardJpaRepository;

    @Test
    void 게시글_전체_조회_테스트() {
        // given
        saveBoards();

        // when
        List<Board> boards = boardQueryDslRepository.findAll(null, 5, null);

        // then
        assertSoftly(softly -> {
            softly.assertThat(boards).hasSize(BOARD_COUNT);
            softly.assertThat(boards.get(0).getId()).isEqualTo(BOARD_COUNT);
        });
    }

    @Test
    void 게시글_ID로_필터링_테스트() {
        // given
        Long lastId = 1L;
        saveBoards();

        // when
        List<Board> boards = boardQueryDslRepository.findAll(lastId, 5, null);

        // then
        boards.forEach(board -> {
            assertThat(board.getId()).isLessThan(lastId);
        });
    }

    private void saveBoards() {
        IntStreams.range(BOARD_COUNT)
            .forEach(n -> boardJpaRepository.save(id_없는_게시글_생성()));
    }
}
