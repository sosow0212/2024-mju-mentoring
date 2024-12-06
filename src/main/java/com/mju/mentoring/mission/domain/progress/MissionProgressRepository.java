package com.mju.mentoring.mission.domain.progress;

import com.mju.mentoring.global.domain.OperateType;
import com.mju.mentoring.global.domain.ResourceType;
import com.mju.mentoring.mission.infrastructure.progress.dto.CurrentProgress;
import java.util.List;
import java.util.Optional;

public interface MissionProgressRepository {

    List<CurrentProgress> findAll(
        final Long challengerId, final ProgressStatus progressStatus, final RewardStatus rewardStatus);

    Optional<MissionProgress> findById(final Long id);

    MissionProgress save(final MissionProgress missionProgress);

    Optional<MissionProgress> findByMissionIdAndChallengerId(
        final Long missionId, final Long challengerId);

    Optional<MissionProgress> findByChallengeIdAndType(final Long challengerId,
        final OperateType getOperateType, final ResourceType resourceType);

    boolean hasChallengedMission(final Long challengerId, final Long missionId);

    List<MissionProgress> findRewardWaitingProgress(Long challengerId);
}
