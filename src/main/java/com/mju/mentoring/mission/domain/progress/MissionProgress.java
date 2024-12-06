package com.mju.mentoring.mission.domain.progress;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MissionProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CurrentInfo currentInfo;

    @Column
    private Long reward;

    @Column
    private Long missionId;

    @Column
    private Long challengerId;

    private MissionProgress(final CurrentInfo currentInfo, final Long missionId,
        final Long reward, final Long challengerId) {
        this.currentInfo = currentInfo;
        this.missionId = missionId;
        this.reward = reward;
        this.challengerId = challengerId;
    }

    public static MissionProgress of(final Long goal, final Long missionId,
        final Long reward, final Long challengerId) {
        return new MissionProgress(CurrentInfo.initProgressInfo(goal), missionId,
            reward, challengerId);
    }

    public void increaseCount() {
        currentInfo.increaseCount();
    }

    public boolean canReceiveReward() {
        return currentInfo.canReceiveReward();
    }

    public void receiveReward() {
        currentInfo.receiveReward();
    }
}
