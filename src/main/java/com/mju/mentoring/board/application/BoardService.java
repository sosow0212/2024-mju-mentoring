package com.mju.mentoring.board.application;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.domain.BoardRepository;
import com.mju.mentoring.board.application.dto.BoardCreateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long save(final BoardCreateRequest boardCreateRequest) {
        Board board = new Board(boardCreateRequest.getTitle(), boardCreateRequest.getContent());
        Board savedBoard = boardRepository.save(board);

        return savedBoard.getId();
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board findById(final Long id) {
        return boardRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public void update(final Long id, final BoardUpdateRequest boardUpdateRequest) {
        this.findById(id)
            .update(boardUpdateRequest.getTitle(), boardUpdateRequest.getContent());
    }

    @Transactional
    public void deleteById(final Long id) {
        boardRepository.deleteById(id);
    }
}
