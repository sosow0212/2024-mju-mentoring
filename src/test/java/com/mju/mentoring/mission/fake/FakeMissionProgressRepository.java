package com.mju.mentoring.mission.fake;

import static com.mju.mentoring.mission.domain.progress.RewardStatus.WAITING;

import com.mju.mentoring.global.domain.OperateType;
import com.mju.mentoring.global.domain.ResourceType;
import com.mju.mentoring.mission.domain.progress.MissionProgress;
import com.mju.mentoring.mission.domain.progress.MissionProgressRepository;
import com.mju.mentoring.mission.domain.progress.ProgressStatus;
import com.mju.mentoring.mission.domain.progress.RewardStatus;
import com.mju.mentoring.mission.infrastructure.progress.dto.CurrentProgress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeMissionProgressRepository implements MissionProgressRepository {

    private Map<Long, MissionProgress> db = new HashMap<>();
    private Long id = 1L;

    @Override
    public List<CurrentProgress> findAll( final Long challengerId,
        final ProgressStatus progressStatus, final RewardStatus rewardStatus) {
        return null;
    }

    @Override
    public Optional<MissionProgress> findById(final Long id) {
        return db.keySet().stream()
            .map(key -> db.get(key))
            .filter(progress -> progress.getId().equals(id))
            .findAny();
    }

    @Override
    public MissionProgress save(final MissionProgress missionProgress) {
        MissionProgress progress = MissionProgress.builder()
            .id(id)
            .currentInfo(missionProgress.getCurrentInfo())
            .challengerId(missionProgress.getChallengerId())
            .missionId(missionProgress.getMissionId())
            .build();
        db.put(id++, progress);
        return progress;
    }

    @Override
    public Optional<MissionProgress> findByMissionIdAndChallengerId(final Long missionId,
        final Long challengerId) {
        return db.keySet().stream()
            .map(key -> db.get(key))
            .filter(progress -> progress.getMissionId().equals(missionId) &&
                progress.getChallengerId().equals(challengerId))
            .findAny();
    }

    @Override
    public Optional<MissionProgress> findByChallengeIdAndType(final Long challengerId,
        final OperateType getOperateType, final ResourceType resourceType) {
        return Optional.empty();
    }

    @Override
    public boolean hasChallengedMission(final Long challengerId, final Long missionId) {
        return db.keySet().stream()
            .map(key -> db.get(key))
            .anyMatch(
                progress -> progress.getMissionId().equals(missionId)
                    && progress.getChallengerId().equals(challengerId));
    }

    @Override
    public List<MissionProgress> findRewardWaitingProgress(final Long challengerId) {
        return db.keySet().stream()
            .map(key -> db.get(key))
            .filter(progress ->
                progress.getCurrentInfo().getRewardStatus().equals(WAITING)
            )
            .toList();
    }
}
