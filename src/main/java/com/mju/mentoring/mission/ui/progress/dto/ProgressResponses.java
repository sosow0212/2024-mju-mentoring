package com.mju.mentoring.mission.ui.progress.dto;

import com.mju.mentoring.mission.application.progress.dto.ProgressResponse;
import java.util.List;

public record ProgressResponses(List<ProgressResponse> progress) {

    public static ProgressResponses of(final List<ProgressResponse> response) {
        return new ProgressResponses(response);
    }
}
