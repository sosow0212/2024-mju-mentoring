package com.mju.mentoring.board.infrastructure;

import com.mju.mentoring.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository extends JpaRepository<Board, Long> {
}
