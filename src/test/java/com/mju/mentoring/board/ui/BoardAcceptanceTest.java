package com.mju.mentoring.board.ui;

import static com.mju.mentoring.board.fixture.BoardFixture.id_없는_게시글_생성;

import com.mju.mentoring.board.domain.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class BoardAcceptanceTest extends BoardAcceptanceTestFixture {

    private static final String 게시글_생성_url = "/boards";
    private static final String 게시글_단건_조회_url = "/boards/1";
    private static final String 게시글_여러건_조회_url = "/boards";
    private static final String 게시글_수정_url = "/boards/1";
    private static final String 게시글_단건_삭제_url = "/boards/1";
    private static final String 게시글_여러건_삭제_url = "/boards";

    private Board 게시글1;
    private Board 게시글2;

    @BeforeEach
    void setUp() {
        게시글1 = id_없는_게시글_생성();
        게시글2 = id_없는_게시글_생성();
    }

    @Test
    void 게시글을_저장_테스트() {
        // given
        var 게시글_생성_요청서 = 게시글_생성_요청();

        // when
        var 게시글_생성_요청_결과 = 게시글을_생성한다(게시글_생성_요청서, 게시글_생성_url);

        // then
        게시글_생성을_검증한다(게시글_생성_요청_결과);
    }

    @Test
    void 게시글_단건_조회_테스트() {
        // given
        var 생성_게시글 = 게시글을_저장한다(게시글1);

        // when
        var 게시글_조회_결과 = 게시글을_단건_조회한다(게시글_단건_조회_url);

        // then
        단건_게시글_조회_검증(게시글_조회_결과, 생성_게시글);
    }

    @Test
    void 게시글_여러건_조회_테스트() {
        // given
        var 생성_게시글1 = 게시글을_저장한다(게시글1);
        var 생성_게시글2 = 게시글을_저장한다(게시글2);

        // when
        var 게시글_조회_결과 = 모든_게시물을_조회한다(게시글_여러건_조회_url);

        // then
        여러_게시물_조회_검증(게시글_조회_결과, 생성_게시글1, 생성_게시글2);
    }

    @Test
    void 게시글_수정_테스트() {
        // given
        게시글을_저장한다(게시글1);
        var 게시글_수정_요청서 = 게시글_수정_요청();

        // when
        var 게시글_수정_결과 = 게시글을_수정한다(게시글_수정_요청서, 게시글_수정_url);

        // then
        게시물_수정_검증(게시글_수정_결과);
    }

    @Test
    void 단건_게시글_삭제_테스트() {
        // given
        게시글을_저장한다(게시글1);

        // when
        var 게시글_삭제_결과 = 단건_게시물을_삭제한다(게시글_단건_삭제_url);

        // then
        게시물_삭제_검증(게시글_삭제_결과);
    }

    @Test
    void 여러건_게시글_삭제_테스트() {
        // given
        게시글을_저장한다(게시글1);
        게시글을_저장한다(게시글2);
        var 여러건_게시글_삭제_요청서 = 여러건_게시글_삭제_요청();

        // when
        var 게시글_삭제_결과 = 여러_게시글을_삭제한다(게시글_여러건_삭제_url, 여러건_게시글_삭제_요청서);

        // then
        게시물_삭제_검증(게시글_삭제_결과);
    }
}
