package com.mju.mentoring.board.domain;

public interface BoardRepository {

    Board save(Board board);

    List<Board> findAll();

    Optional<Board> findById(Long id);
}
