package com.mju.mentoring.board.infrastructure;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.domain.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaBoardRepositoryImpl implements BoardRepository {

    private final BoardJpaRepository boardJpaRepository;

    @Override
    public void save(final Board board) {
        boardJpaRepository.save(board);
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
