package com.mju.mentoring.board.fixture;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.domain.BoardText;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class BoardFixtures {

    public static Board 게시글_id_있음() {
        return Board.builder()
                .id(1L)
                .boardText(new BoardText("title", "content"))
                .build();
    }

    public static Board 게시글_id_없음() {
        return new Board("title", "content");
    }
}
