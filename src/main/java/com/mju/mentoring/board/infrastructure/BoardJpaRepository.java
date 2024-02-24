package com.mju.mentoring.board.infrastructure;

import com.mju.mentoring.board.domain.Board;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

public interface BoardJpaRepository extends JpaRepository<Board, Long> {

    Board save(final Board board);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
        @QueryHint(name = "javax.persistence.lock.timeout", value = "2000")
    })
    Optional<Board> findById(final Long id);

    List<Board> findAll();

    void delete(final Board board);

    void deleteAllByIdInBatch(final Iterable<Long> ids);
}
