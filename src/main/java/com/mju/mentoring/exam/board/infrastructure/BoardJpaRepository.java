package com.mju.mentoring.exam.board.infrastructure;

import com.mju.mentoring.exam.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository extends JpaRepository<Board, Long> {
}
