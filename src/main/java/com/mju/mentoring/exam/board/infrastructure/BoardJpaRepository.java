package com.mju.mentoring.exam.board.infrastructure;

import com.mju.mentoring.exam.board.domain.Board;
import com.mju.mentoring.exam.board.domain.BoardRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardJpaRepository extends JpaRepository<Board, Long> {
}
