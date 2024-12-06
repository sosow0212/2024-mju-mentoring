package com.mju.mentoring.mission.application.progress;

import com.mju.mentoring.board.domain.BoardOperateEvent;
import com.mju.mentoring.mission.domain.mission.ChallengedMissionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ProgressEventHandler {

    private final ProgressService progressService;

    @EventListener
    public void challengeMission(final ChallengedMissionEvent event) {
        progressService.challengeMission(
            event.getChallengerId(), event.getMissionId(), event.getReward(), event.getGoal());
    }

    @TransactionalEventListener(
        classes = BoardOperateEvent.class,
        phase = TransactionPhase.AFTER_COMMIT
    )
    @Async
    public void targetOperate(final BoardOperateEvent event) {
        progressService.increaseTargetProgress(
            event.getMemberId(), event.getOperateType(), event.getResourceType());
    }
}
