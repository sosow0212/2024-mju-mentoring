package com.mju.mentoring.board.domain;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    Board save(final Board board);

    List<Board> findAll();

    Optional<Board> findById(final Long id);

    void delete(final Board board);

    void deleteAllById(final List<Long> ids);

    List<Board> findBoardsByWriterId(final Long writerId);
}
