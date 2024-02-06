package com.mju.mentoring.board.service.dto;

import jakarta.validation.constraints.NotBlank;

public record BoardTextUpdateRequest(

        @NotBlank(message = "제목이 작성되어야 합니다.")
        String title,

        @NotBlank(message = "글이 작성되어야 합니다.")
        String content) {
}
