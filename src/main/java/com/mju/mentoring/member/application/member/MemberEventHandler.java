package com.mju.mentoring.member.application.member;

import com.mju.mentoring.mission.domain.progress.RewardReceivedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MemberEventHandler {

    private final MemberService memberService;

    @TransactionalEventListener(
        classes = RewardReceivedEvent.class,
        phase = TransactionPhase.AFTER_COMMIT
    )
    @Async
    public void receiveReward(final RewardReceivedEvent event) {
        memberService.increasePoint(event.getChallengerId(), event.getReward());
    }
}
