package com.mju.mentoring.mission.infrastructure.progress;

import com.mju.mentoring.global.domain.OperateType;
import com.mju.mentoring.global.domain.ResourceType;
import com.mju.mentoring.mission.domain.progress.MissionProgress;
import com.mju.mentoring.mission.domain.progress.MissionProgressRepository;
import com.mju.mentoring.mission.domain.progress.ProgressStatus;
import com.mju.mentoring.mission.domain.progress.RewardStatus;
import com.mju.mentoring.mission.infrastructure.progress.dto.CurrentProgress;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MissionProgressRepositoryImpl implements MissionProgressRepository {

    private final MissionProgressJpaRepository missionProgressJpaRepository;
    private final MissionProgressQueryDslRepository queryDslRepository;

    @Override
    public List<CurrentProgress> findAll(final Long challengerId,
        final ProgressStatus progressStatus, final RewardStatus rewardStatus) {
        return queryDslRepository.findAll(challengerId, progressStatus, rewardStatus);
    }

    @Override
    public Optional<MissionProgress> findById(final Long id) {
        return missionProgressJpaRepository.findById(id);
    }

    @Override
    public MissionProgress save(final MissionProgress missionProgress) {
        return missionProgressJpaRepository.save(missionProgress);
    }

    @Override
    public Optional<MissionProgress> findByMissionIdAndChallengerId(final Long missionId,
        final Long challengerId) {
        return missionProgressJpaRepository.findByMissionIdAndChallengerId(missionId, challengerId);
    }

    @Override
    public Optional<MissionProgress> findByChallengeIdAndType(final Long challengerId,
        final OperateType operateType, final ResourceType resourceType) {
        return queryDslRepository.findByChallengeIdAndType(challengerId, operateType, resourceType);
    }

    @Override
    public boolean hasChallengedMission(final Long challengerId, final Long missionId) {
        return queryDslRepository.hasAlreadyChallengedMission(challengerId, missionId);
    }

    @Override
    public List<MissionProgress> findRewardWaitingProgress(final Long challengerId) {
        return queryDslRepository.findRewardWaitingProgress(challengerId);
    }
}
