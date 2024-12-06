package com.mju.mentoring.mission.fixture;

import static com.mju.mentoring.mission.domain.progress.ProgressStatus.COMPLETED;
import static com.mju.mentoring.mission.domain.progress.ProgressStatus.IN_PROGRESS;
import static com.mju.mentoring.mission.domain.progress.RewardStatus.NOT_AVAILABLE;
import static com.mju.mentoring.mission.domain.progress.RewardStatus.RECEIVED;
import static com.mju.mentoring.mission.domain.progress.RewardStatus.WAITING;

import com.mju.mentoring.mission.domain.progress.CurrentInfo;
import com.mju.mentoring.mission.domain.progress.MissionProgress;
import com.mju.mentoring.mission.domain.progress.ProgressInfo;

public class ProgressFixture {

    public static MissionProgress id_없는_완료된_진행도_생성() {
        return MissionProgress.builder()
            .challengerId(1L)
            .missionId(1L)
            .currentInfo(CurrentInfo.builder()
                .progressInfo(ProgressInfo.builder()
                    .progressStatus(COMPLETED)
                    .build())
                .build())
            .build();
    }

    public static MissionProgress id_없는_진행중인_진행도_생성() {
        return MissionProgress.builder()
            .challengerId(1L)
            .missionId(1L)
            .currentInfo(CurrentInfo.builder()
                .rewardStatus(NOT_AVAILABLE)
                .progressInfo(ProgressInfo.builder()
                    .progressStatus(IN_PROGRESS)
                    .build())
                .build())
            .build();
    }

    public static MissionProgress id_없는_보상_수령_가능한_진행도_생성() {
        return MissionProgress.builder()
            .challengerId(1L)
            .missionId(1L)
            .currentInfo(CurrentInfo.builder()
                .rewardStatus(WAITING)
                .progressInfo(ProgressInfo.builder()
                    .progressStatus(COMPLETED)
                    .build())
                .build())
            .build();
    }

    public static MissionProgress id_없는_보상_수령한_진행도_생성() {
        return MissionProgress.builder()
            .challengerId(1L)
            .missionId(1L)
            .currentInfo(CurrentInfo.builder()
                .rewardStatus(RECEIVED)
                .build())
            .build();
    }
}
