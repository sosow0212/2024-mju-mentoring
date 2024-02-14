package com.mju.mentoring.exam.board.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mju.mentoring.exam.board.domain.Board;
import com.mju.mentoring.exam.board.domain.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BoardRepositoryImpl implements BoardRepository {

	private final BoardJpaRepository boardJpaRepository;

	@Override
	public Board save(final Board board) {
		return boardJpaRepository.save(board);
	}

	@Override
	public Optional<Board> findById(final Long id) {
		return boardJpaRepository.findById(id);
	}

	@Override
	public List<Board> findAll() {
		return boardJpaRepository.findAll();
	}

	@Override
	public void deleteById(final Long id) {
		boardJpaRepository.deleteById(id);
	}
}
