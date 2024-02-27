package com.mju.mentoring.board.infrastructure;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.domain.BoardRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardJpaRepository boardJpaRepository;

    @Override
    public Board save(final Board board) {
        return boardJpaRepository.save(board);
    }

    @Override
    public List<Board> findAll() {
        return boardJpaRepository.findAll();
    }

    @Override
    public Optional<Board> findById(final Long id) {
        return boardJpaRepository.findById(id);
    }

    @Override
    public void delete(final Board board) {
        boardJpaRepository.delete(board);
    }

    @Override
    public void deleteAllById(final List<Long> ids) {
        boardJpaRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public List<Board> findBoardsByWriterId(final Long writerId) {
        return boardJpaRepository.findAllByWriterId(writerId);
    }
}
