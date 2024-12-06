package com.mju.mentoring.mission.application.progress;

import static com.mju.mentoring.mission.fixture.ProgressFixture.id_없는_보상_수령_가능한_진행도_생성;
import static com.mju.mentoring.mission.fixture.ProgressFixture.id_없는_진행중인_진행도_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.mju.mentoring.mission.domain.progress.MissionProgress;
import com.mju.mentoring.mission.domain.progress.MissionProgressRepository;
import com.mju.mentoring.mission.domain.progress.RewardStatus;
import com.mju.mentoring.mission.exception.exceptions.AlreadyChallengeMission;
import com.mju.mentoring.mission.exception.exceptions.CannotReceiveRewardException;
import com.mju.mentoring.mission.exception.exceptions.InvalidChallengerException;
import com.mju.mentoring.mission.exception.exceptions.NoCompletedProgressException;
import com.mju.mentoring.mission.fake.FakeMissionProgressRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProgressServiceTest {

    private static final Long FIRST_PROGRESS_ID = 1L;
    private static final Long DEFAULT_CHALLENGER_ID = 1L;
    private static final Long DEFAULT_MISSION_ID = 1L;
    private static final Long DEFAULT_REWARD = 1000L;
    private static final Long DEFAULT_GOAL = 5L;

    private ProgressService progressService;
    private MissionProgressRepository missionProgressRepository;

    @BeforeEach
    void setUp() {
        missionProgressRepository = new FakeMissionProgressRepository();
        progressService = new ProgressService(missionProgressRepository);
    }

    @Test
    void 미션_신청_테스트() {
        // when
        progressService.challengeMission(DEFAULT_CHALLENGER_ID, DEFAULT_MISSION_ID, DEFAULT_REWARD,
            DEFAULT_GOAL);
        Optional<MissionProgress> progress = missionProgressRepository.findById(1L);

        // then
        assertThat(progress).isNotEmpty();
    }

    @Test
    void 이미_신청_중인_미션_예외_테스트() {
        // given
        progressService.challengeMission(
            DEFAULT_CHALLENGER_ID, DEFAULT_MISSION_ID, DEFAULT_REWARD, DEFAULT_GOAL);

        // when & then
        assertThatThrownBy(
            () -> progressService.challengeMission(
                DEFAULT_CHALLENGER_ID, DEFAULT_MISSION_ID, DEFAULT_REWARD, DEFAULT_GOAL))
            .isInstanceOf(AlreadyChallengeMission.class);
    }

    @Test
    void 보상_수령_테스트() {
        // given
        missionProgressRepository.save(id_없는_보상_수령_가능한_진행도_생성());

        // when
        progressService.receiveReward(DEFAULT_CHALLENGER_ID, FIRST_PROGRESS_ID);

        // then
        Optional<MissionProgress> progress = missionProgressRepository.findById(FIRST_PROGRESS_ID);
        assertSoftly(softly -> {
            softly.assertThat(progress).isNotEmpty();
            softly.assertThat(progress.get()
                .getCurrentInfo().getRewardStatus()).isEqualTo(
                RewardStatus.RECEIVED);
        });
    }

    @Test
    void 보상_수령_불가_예외_테스트() {
        // given
        missionProgressRepository.save(id_없는_진행중인_진행도_생성());

        // when & then
        assertThatThrownBy(
            () -> progressService.receiveReward(DEFAULT_CHALLENGER_ID, FIRST_PROGRESS_ID))
            .isInstanceOf(CannotReceiveRewardException.class);
    }

    @Test
    void 잘못된_도전자_보상_수령_예외_테스트() {
        // given
        missionProgressRepository.save(id_없는_보상_수령_가능한_진행도_생성());

        // when & then
        assertThatThrownBy(
            () -> progressService.receiveReward(2L, FIRST_PROGRESS_ID))
            .isInstanceOf(InvalidChallengerException.class);
    }

    @Test
    void 전체_보상_수령_예외_테스트() {
        // when & then
        assertThatThrownBy(() -> progressService.receiveAllRewards(DEFAULT_CHALLENGER_ID))
            .isInstanceOf(NoCompletedProgressException.class);
    }
}
