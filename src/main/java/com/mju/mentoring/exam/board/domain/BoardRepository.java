package com.mju.mentoring.exam.board.domain;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    Board save(final Board board);

    Optional<Board> findById(final Long id);

    List<Board> findAll();

    void deleteById(final Long id);
}
