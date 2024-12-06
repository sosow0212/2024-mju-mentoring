package com.mju.mentoring.mission.domain.progress;

import static com.mju.mentoring.mission.domain.progress.ProgressStatus.COMPLETED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Embeddable
public class ProgressInfo {

    private static final Double INITIAL_PROGRESS = 0.0;
    private static final Double PERCENTAGE_MULTIPLIER = 100.0;
    private static final Double MAX_PROGRESS = 100.0;

    @Enumerated(EnumType.STRING)
    private ProgressStatus progressStatus;

    @Column
    private Long goal;

    @Column
    private Double progress;

    public static ProgressInfo createDefault(final Long goal) {
        return new ProgressInfo(ProgressStatus.IN_PROGRESS, goal, INITIAL_PROGRESS);
    }

    public void synchronizeProgress(final Long currentCount) {
        progress = currentCount * PERCENTAGE_MULTIPLIER / goal;
        if (progress >= MAX_PROGRESS) {
            progress = MAX_PROGRESS;
            completeMission();
        }
    }

    public boolean isSatisfiedGoal() {
        return progressStatus.equals(COMPLETED);
    }

    private void completeMission() {
        progressStatus = COMPLETED;
    }
}
