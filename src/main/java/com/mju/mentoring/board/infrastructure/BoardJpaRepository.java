package com.mju.mentoring.board.infrastructure;

import com.mju.mentoring.board.domain.Board;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository extends JpaRepository<Board, Long> {

    Board save(final Board board);

    Optional<Board> findById(final Long id);

    List<Board> findAll();

    void delete(final Board board);

    void deleteAllByIdInBatch(final Iterable<Long> ids);
}
