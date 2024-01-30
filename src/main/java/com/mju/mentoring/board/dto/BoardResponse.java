package com.mju.mentoring.board.dto;

import com.mju.mentoring.board.domain.Board;

// 게시글 상세 정보 응답 DTO
public record BoardResponse(Long id, String title, String content) {
    public static BoardResponse fromBoard(Board board) {
        return new BoardResponse(board.getId(), board.getBoardContent().getTitle(), board.getBoardContent().getContent());
    }
}