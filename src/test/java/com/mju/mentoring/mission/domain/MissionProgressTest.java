package com.mju.mentoring.mission.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.mju.mentoring.mission.domain.progress.MissionProgress;
import com.mju.mentoring.mission.domain.progress.RewardStatus;
import com.mju.mentoring.mission.exception.exceptions.CannotReceiveRewardException;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Test;

class MissionProgressTest {

    private static final Long DEFAULT_GOAL = 5L;
    private static final Long DEFAULT_MISSION_ID = 1L;
    private static final Long DEFAULT_CHALLENGER_ID = 1L;
    private static final Long DEFAULT_REWARD = 1000L;


    @Test
    void 진행도_증가_테스트() {
        // given
        MissionProgress progress = createDefaultProgress();

        // when
        progress.increaseCount();

        // then
        assertSoftly(softly -> {
            softly.assertThat(progress.getCurrentInfo().getCurrentCount()).isEqualTo(1L);
            softly.assertThat(progress.getCurrentInfo()
                .getProgressInfo()
                .getProgress()).isEqualTo(20);
        });
    }

    @Test
    void 미션_성공_테스트() {
        // given
        MissionProgress progress = createDefaultProgress();

        // when
        succeedMission(progress);

        // then
        assertSoftly(softly -> {
            softly.assertThat(progress.getCurrentInfo().getCurrentCount())
                .isEqualTo(DEFAULT_GOAL);
            softly.assertThat(progress.getCurrentInfo().getProgressInfo().getProgress())
                .isEqualTo(100.0);
            softly.assertThat(progress.getCurrentInfo().getProgressInfo().isSatisfiedGoal())
                .isTrue();
        });
    }

    @Test
    void 미션_성공_시_보상_수령_상태_테스트() {
        // given
        MissionProgress progress = createDefaultProgress();

        // when
        succeedMission(progress);

        // then
        assertThat(progress.canReceiveReward())
            .isTrue();
    }

    @Test
    void 보상_수령_상태_변경_테스트() {
        // given
        MissionProgress progress = createDefaultProgress();

        // when
        succeedMission(progress);
        progress.receiveReward();

        // then
        assertSoftly(softly -> {
            softly.assertThat(progress.getCurrentInfo().getRewardStatus())
                .isEqualTo(RewardStatus.RECEIVED);
            softly.assertThat(progress.canReceiveReward()).isFalse();
        });
    }

    @Test
    void 미션_완료_전_보상_수령_시_예외_테스트() {
        // given
        MissionProgress progress = createDefaultProgress();

        // when & then
        assertThatThrownBy(progress::receiveReward)
            .isInstanceOf(CannotReceiveRewardException.class);
    }

    private static void succeedMission(final MissionProgress progress) {
        LongStream.range(0L, DEFAULT_GOAL)
            .forEach(n -> progress.increaseCount());
    }

    private static MissionProgress createDefaultProgress() {
        return MissionProgress.of(
            DEFAULT_GOAL, DEFAULT_MISSION_ID, DEFAULT_REWARD, DEFAULT_CHALLENGER_ID);
    }
}
