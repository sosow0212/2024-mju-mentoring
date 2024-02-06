package com.mju.mentoring.board.service;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.domain.BoardRepository;
import com.mju.mentoring.board.exception.exceptions.BoardNotFoundException;
import com.mju.mentoring.board.service.dto.BoardCreateRequest;
import com.mju.mentoring.board.service.dto.BoardTextUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long save(final BoardCreateRequest request) {
        Board newBoard = new Board(request.title(), request.content());
        boardRepository.save(newBoard);

        return newBoard.getId();
    }

    public Board searchById(final Long id) {
        return findById(id);
    }

    private Board findById(final Long id) {
        return boardRepository.findById(id)
                .orElseThrow(BoardNotFoundException::new);
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Transactional
    public void updateText(final Long id, final BoardTextUpdateRequest request) {
        Board targetBoard = findById(id);

        targetBoard.updateText(request.title(), request.content());
    }

    @Transactional
    public void deleteById(final Long id) {
        Board targetBoard = findById(id);
        boardRepository.delete(targetBoard);
    }
}
