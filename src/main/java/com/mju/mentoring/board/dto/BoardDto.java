package com.mju.mentoring.board.dto;

import com.mju.mentoring.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardDto {

    private Long id;
    private String title;
    private String content;

    // 정적 팩토리 메서드 추가 (현재 사용 안함)
    /*public static BoardDto fromBoard(Board board) {
        return new BoardDto(board.getId(), board.getBoardContent().getTitle(), board.getBoardContent().getContent());
    }*/
}
