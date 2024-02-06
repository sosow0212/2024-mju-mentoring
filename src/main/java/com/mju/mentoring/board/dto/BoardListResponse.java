package com.mju.mentoring.board.dto;

import com.mju.mentoring.board.domain.Board;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

// 게시글 목록 조회 응답 DTO
public record BoardListResponse(Long id, String title) {
    public static BoardListResponse fromBoard(Board board) {
        return new BoardListResponse(board.getId(), board.getBoardContent().getTitle());
    }
}
