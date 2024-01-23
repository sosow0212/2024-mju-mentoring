package com.mju.mentoring.board.infrastructure;

import com.mju.mentoring.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardJpaRepository extends JpaRepository<Board, Long> {

    Board save(final Board board);

    Optional<Board> findById(final Long id);

    List<Board> findAll();

    void deleteById(final Long id);
}
