package com.mju.mentoring.exam.board.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.mju.mentoring.exam.board.domain.Board;

import jakarta.persistence.LockModeType;

public interface BoardJpaRepository extends JpaRepository<Board, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Board> findById(final Long id);
}
