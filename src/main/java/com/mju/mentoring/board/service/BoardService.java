package com.mju.mentoring.board.service;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.domain.BoardRepository;
import com.mju.mentoring.board.service.dto.BoardCreateRequest;
import com.mju.mentoring.board.service.dto.BoardUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long save(final BoardCreateRequest request) {
        Board newBoard = Board.of(request.title(), request.content());
        return boardRepository.save(newBoard);
    }

    public Board find(final Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당 게시물이 없습니다."));
    }

    @Transactional
    public void update(final Long id, final BoardUpdateRequest request) {
        Board targetBoard = find(id);

        targetBoard.updateTitle(request.title());
        targetBoard.updateContent(request.content());
    }

    @Transactional
    public void delete(final Long id) {
        boardRepository.deleteById(id);
    }
}
