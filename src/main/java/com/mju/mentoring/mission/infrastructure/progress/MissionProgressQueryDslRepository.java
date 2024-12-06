package com.mju.mentoring.mission.infrastructure.progress;

import static com.mju.mentoring.mission.domain.mission.QMission.mission;
import static com.mju.mentoring.mission.domain.progress.QMissionProgress.missionProgress;
import static com.mju.mentoring.mission.domain.progress.RewardStatus.WAITING;

import com.mju.mentoring.global.domain.OperateType;
import com.mju.mentoring.global.domain.ResourceType;
import com.mju.mentoring.mission.domain.progress.MissionProgress;
import com.mju.mentoring.mission.domain.progress.ProgressStatus;
import com.mju.mentoring.mission.domain.progress.RewardStatus;
import com.mju.mentoring.mission.infrastructure.progress.dto.CurrentProgress;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MissionProgressQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<MissionProgress> findByChallengeIdAndType(final Long challengerId,
        final OperateType operateType, final ResourceType resourceType) {
        return Optional.of(queryFactory.selectFrom(missionProgress)
            .innerJoin(mission)
            .on(mission.operateType.eq(operateType)
                .and(mission.targetType.eq(resourceType)))
            .where(missionProgress.challengerId.eq(challengerId))
            .fetchFirst());
    }

    public boolean hasAlreadyChallengedMission(final Long challengerId, final Long missionId) {
        return queryFactory.selectOne()
            .from(missionProgress)
            .where(
                missionProgress.challengerId.eq(challengerId),
                missionProgress.missionId.eq(missionId)
            )
            .fetchFirst() != null;
    }

    public List<CurrentProgress> findAll(final Long challengerId,
        final ProgressStatus progressStatus, final RewardStatus rewardStatus) {
        return queryFactory.select(Projections.constructor(
                CurrentProgress.class,
                mission.title,
                missionProgress.currentInfo.currentCount,
                missionProgress.currentInfo.progressInfo.goal,
                missionProgress.currentInfo.progressInfo.progress,
                missionProgress.currentInfo.progressInfo.progressStatus,
                missionProgress.currentInfo.rewardStatus
            ))
            .from(missionProgress)
            .innerJoin(mission)
            .on(missionProgress.missionId.eq(mission.id))
            .where(missionProgress.challengerId.eq(challengerId),
                sameProgressStatus(progressStatus), sameRewardStatus(rewardStatus))
            .fetchJoin()
            .fetch();
    }

    public List<MissionProgress> findRewardWaitingProgress(final Long challengerId) {
        return queryFactory.selectFrom(missionProgress)
            .where(missionProgress.challengerId.eq(challengerId),
                missionProgress.currentInfo.rewardStatus.eq(WAITING))
            .fetch();
    }

    private BooleanExpression sameProgressStatus(final ProgressStatus progressStatus) {
        if (progressStatus == null) {
            return null;
        }
        return missionProgress.currentInfo.progressInfo.progressStatus.eq(progressStatus);
    }

    private BooleanExpression sameRewardStatus(final RewardStatus rewardStatus) {
        if (rewardStatus == null) {
            return null;
        }
        return missionProgress.currentInfo.rewardStatus.eq(rewardStatus);
    }
}
