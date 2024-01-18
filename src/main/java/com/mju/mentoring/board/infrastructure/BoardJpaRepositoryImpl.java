package com.mju.mentoring.board.infrastructure;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.domain.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardJpaRepositoryImpl implements BoardRepository {

    private final BoardJpaRepository boardJpaRepository;

    @Override
    public Long save(final Board board) {
        boardJpaRepository.save(board);
        return board.getId();
    }

    @Override
    public Optional<Board> findById(final Long id) {
        return boardJpaRepository.findById(id);
    }

    @Override
    public void deleteById(final Long id) {
        boardJpaRepository.deleteById(id);
    }
}
