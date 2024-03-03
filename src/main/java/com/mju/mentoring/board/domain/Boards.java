package com.mju.mentoring.board.domain;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Boards {

    private final List<Board> boards;

    public static Boards from(final List<Board> boards) {
        return new Boards(boards);
    }

    public void verifyAllWriter(final Long writerId) {
        boards.forEach(board -> board.verifyWriter(writerId));
    }
}
