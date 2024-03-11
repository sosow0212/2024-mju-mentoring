package com.mju.mentoring.board.domain;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    Board save(final Board board);

    List<Board> findAll(final Long boardId, final int size, final String search);

    Optional<Board> findById(final Long id);

    Optional<Board> viewById(final Long id);

    void delete(final Board board);

    void deleteAllById(final List<Long> ids);

    List<Board> findBoardsByBoardsId(final List<Long> ids);

    void updateWriterName(final Long writerId, final String newWriterName);
}
