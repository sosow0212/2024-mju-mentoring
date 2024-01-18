package com.mju.mentoring.board.domain;

import java.util.Optional;

public interface BoardRepository {

    Board save(final Board board);
    Optional<Board> findById(final Long boardId);
    void deleteById(final Long id);
}
