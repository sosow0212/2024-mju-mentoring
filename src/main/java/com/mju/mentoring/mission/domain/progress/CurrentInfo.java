package com.mju.mentoring.mission.domain.progress;

import static com.mju.mentoring.mission.domain.progress.RewardStatus.RECEIVED;
import static com.mju.mentoring.mission.domain.progress.RewardStatus.WAITING;

import com.mju.mentoring.mission.exception.exceptions.CannotReceiveRewardException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
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
public class CurrentInfo {

    private static final Long INITIAL_CURRENT_COUNT = 0L;

    @Enumerated(EnumType.STRING)
    private RewardStatus rewardStatus;

    @Column
    private Long currentCount;

    @Embedded
    private ProgressInfo progressInfo;

    public static CurrentInfo initProgressInfo(final Long goal) {
        return new CurrentInfo(
            RewardStatus.NOT_AVAILABLE, INITIAL_CURRENT_COUNT, ProgressInfo.createDefault(goal));
    }

    public boolean canReceiveReward() {
        return progressInfo.isSatisfiedGoal() && rewardStatus.canReceive();
    }

    public void increaseCount() {
        currentCount++;
        progressInfo.synchronizeProgress(currentCount);

        if (progressInfo.isSatisfiedGoal()) {
            readyToReceiveReward();
        }
    }

    public void receiveReward() {
        if (!canReceiveReward()) {
            throw new CannotReceiveRewardException();
        }
        rewardStatus = RECEIVED;
    }

    private void readyToReceiveReward() {
        rewardStatus = WAITING;
    }
}
