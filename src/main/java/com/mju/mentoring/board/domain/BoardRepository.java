package com.mju.mentoring.board.domain;

import java.util.Optional;

public interface BoardRepository {

    void save(final Board board);
    Optional<Board> findById(final Long id);
    void deleteById(final Long id);
}
