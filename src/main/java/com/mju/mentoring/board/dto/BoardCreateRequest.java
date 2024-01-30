package com.mju.mentoring.board.dto;

// 게시글 생성 요청 DTO
public record BoardCreateRequest(String title, String content) {
}