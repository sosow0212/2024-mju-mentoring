package com.mju.mentoring.mission.infrastructure.progress.dto;

import com.mju.mentoring.mission.application.progress.dto.ProgressResponse;
import com.mju.mentoring.mission.domain.progress.ProgressStatus;
import com.mju.mentoring.mission.domain.progress.RewardStatus;

public record CurrentProgress(String missionTitle, Long currentCount, Long goal, Double progress,
                              ProgressStatus progressStatus, RewardStatus rewardStatus) {

    public ProgressResponse toResponse() {
        return new ProgressResponse(
            missionTitle, currentCount, goal, progress, progressStatus, rewardStatus);
    }
}
