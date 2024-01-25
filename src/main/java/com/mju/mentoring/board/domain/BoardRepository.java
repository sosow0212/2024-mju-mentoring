package com.mju.mentoring.board.domain;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    void save(final Board board);
    Optional<Board> findById(final Long id);
    List<Board> findAll();
    void delete(final Board board);
}
