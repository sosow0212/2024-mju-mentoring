package com.mju.mentoring.board.service;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.domain.BoardRepository;
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
        Board board = new Board(request.getTitle(), request.getContent());
        Board savedBoard = boardRepository.save(board);

        return savedBoard.getId();
    }

    @Transactional(readOnly = true)
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Board findById(final Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없다"));
    }

    @Transactional
    public void update(final Long id, final BoardUpdateRequest request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없다"));

        board.update(request.getTitle(), request.getContent());
    }

    @Transactional
    public void delete(final Long id) {
        boardRepository.deleteById(id);
    }
}
