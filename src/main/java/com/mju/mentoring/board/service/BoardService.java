package com.mju.mentoring.board.service;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.domain.BoardRepository;
import com.mju.mentoring.board.exception.exceptions.BoardNotFoundException;
import com.mju.mentoring.board.service.dto.BoardCreateRequest;
import com.mju.mentoring.board.service.dto.BoardUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long save(final BoardCreateRequest request) {
        Board board = new Board(request.title(), request.content());
        Board savedBoard = boardRepository.save(board);

        return savedBoard.getId();
    }

    @Transactional(readOnly = true)
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Board findById(final Long id) {
        return findBoard(id);
    }

    private Board findBoard(final Long id) {
        return boardRepository.findById(id)
                .orElseThrow(BoardNotFoundException::new);
    }

    @Transactional // 쓰기 지연, 변경 감지
    public void update(final Long id, final BoardUpdateRequest request) {
        Board board = findBoard(id);

        board.update(request.getTitle(), request.getContent());
    }

    @Transactional
    public void delete(final Long id) {
        boardRepository.deleteById(id);
    }
}
