package com.mju.mentoring.mission.domain.progress;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RewardReceivedEvent {

    private final Long challengerId;
    private final Long reward;
}
