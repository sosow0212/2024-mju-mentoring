package com.mju.mentoring.mission.application.progress.dto;

import com.mju.mentoring.mission.domain.progress.ProgressStatus;
import com.mju.mentoring.mission.domain.progress.RewardStatus;

public record ProgressResponse(String missionTitle, Long currentCount, Long goal, Double progress,
                               ProgressStatus progressStatus, RewardStatus rewardStatus) {

}
