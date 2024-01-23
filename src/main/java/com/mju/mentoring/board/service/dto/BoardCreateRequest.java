package com.mju.mentoring.board.service.dto;

public record BoardCreateRequest(
        String title,
        String content
) {
}
