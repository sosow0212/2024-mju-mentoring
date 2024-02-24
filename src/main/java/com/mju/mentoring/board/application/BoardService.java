package com.mju.mentoring.board.application;

import com.mju.mentoring.board.application.dto.BoardDeleteRequest;
import com.mju.mentoring.board.application.dto.BoardUpdateRequest;
import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.domain.BoardRepository;
import com.mju.mentoring.board.application.dto.BoardCreateRequest;
import com.mju.mentoring.board.exception.exceptions.BoardNotFoundException;
import com.mju.mentoring.member.application.auth.AuthService;
import com.mju.mentoring.member.domain.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final AuthService authService;
    private final BoardRepository boardRepository;

    @Transactional
    public Long save(final Long writerId, final BoardCreateRequest boardCreateRequest) {
        Member writer = authService.findMemberById(writerId);
        Board board = Board.of(writer.getId(), writer.getNickname(), boardCreateRequest.title(),
            boardCreateRequest.content());
        Board savedBoard = boardRepository.save(board);

        return savedBoard.getId();
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Transactional
    public Board readBoard(final Long id) {
        Board board = findById(id);
        board.viewBoard();
        return board;
    }

    public Board findById(final Long id) {
        return boardRepository.findById(id)
            .orElseThrow(BoardNotFoundException::new);
    }

    @Transactional
    public void update(final Long writerId, final Long id,
        final BoardUpdateRequest boardUpdateRequest) {
        this.findById(id)
            .update(writerId, boardUpdateRequest.title(), boardUpdateRequest.content());
    }

    @Transactional
    public void deleteById(final Long writerId, final Long id) {
        Board board = findById(id);
        board.verifyWriter(writerId);
        boardRepository.delete(board);
    }

    @Transactional
    public void deleteAllById(final BoardDeleteRequest deleteRequest) {
        boardRepository.deleteAllById(deleteRequest.ids());
    }
}
