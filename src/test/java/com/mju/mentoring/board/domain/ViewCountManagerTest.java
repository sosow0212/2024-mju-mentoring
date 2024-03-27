package com.mju.mentoring.board.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mju.mentoring.board.infrastructure.MemoryViewCountManager;
import org.junit.jupiter.api.Test;

class ViewCountManagerTest {

    private static final Long DEFAULT_WRITER_ID = 1L;
    private static final Long DEFAULT_BOARD_ID = 1L;

    private ViewCountManager viewCountManager = new MemoryViewCountManager();

    @Test
    void 조회수_치팅_방지_테스트() {
        // given
        viewCountManager.read(DEFAULT_BOARD_ID, DEFAULT_WRITER_ID);

        // when & then
        assertThat(viewCountManager.isAlreadyRead(DEFAULT_BOARD_ID, DEFAULT_WRITER_ID))
            .isTrue();
    }

}
