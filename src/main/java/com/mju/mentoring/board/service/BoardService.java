package com.mju.mentoring.board.service;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.domain.BoardRepository;
import com.mju.mentoring.board.service.dto.BoardCreateRequest;
import com.mju.mentoring.board.service.dto.BoardTextUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Board find(final Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당 게시물이 없습니다."));
    }

    @Transactional
    public void updateText(final Long id, final BoardTextUpdateRequest request) {
        Board targetBoard = find(id);

        targetBoard.updateText(request.title(), request.content());
    }

    @Transactional
    public void delete(final Long id) {
        boardRepository.deleteById(id);
    }
}
