package com.mju.mentoring.mission.infrastructure.progress;

import static com.mju.mentoring.mission.domain.progress.ProgressStatus.COMPLETED;
import static com.mju.mentoring.mission.domain.progress.ProgressStatus.IN_PROGRESS;
import static com.mju.mentoring.mission.domain.progress.RewardStatus.RECEIVED;
import static com.mju.mentoring.mission.domain.progress.RewardStatus.WAITING;
import static com.mju.mentoring.mission.fixture.MissionFixture.id_없는_미션_생성;
import static com.mju.mentoring.mission.fixture.ProgressFixture.id_없는_보상_수령_가능한_진행도_생성;
import static com.mju.mentoring.mission.fixture.ProgressFixture.id_없는_보상_수령한_진행도_생성;
import static com.mju.mentoring.mission.fixture.ProgressFixture.id_없는_완료된_진행도_생성;
import static com.mju.mentoring.mission.fixture.ProgressFixture.id_없는_진행중인_진행도_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.mju.mentoring.global.DatabaseCleaner;
import com.mju.mentoring.global.config.TestQuerydslConfig;
import com.mju.mentoring.global.domain.OperateType;
import com.mju.mentoring.global.domain.ResourceType;
import com.mju.mentoring.mission.domain.mission.Mission;
import com.mju.mentoring.mission.domain.progress.MissionProgress;
import com.mju.mentoring.mission.infrastructure.mission.MissionJpaRepository;
import com.mju.mentoring.mission.infrastructure.progress.dto.CurrentProgress;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@DatabaseCleaner
@Import(TestQuerydslConfig.class)
class MissionProgressQueryDslRepositoryTest {

    private final static Long DEFAULT_MISSION_ID = 1L;
    private final static Long DEFAULT_CHALLENGER_ID = 1L;
    private final static Long DEFAULT_GOAL = 5L;
    private final static Long DEFAULT_REWARD = 1000L;
    private static final ResourceType DEFAULT_RESOURCE_TYPE = ResourceType.BOARD;
    private static final OperateType DEFAULT_OPERATE_TYPE = OperateType.CREATE;

    @Autowired
    private MissionJpaRepository missionRepository;

    @Autowired
    private MissionProgressJpaRepository missionProgressJpaRepository;

    @Autowired
    private MissionProgressQueryDslRepository queryDslRepository;

    @Test
    void 도전자와_미션_타입으로_미션_진행도_조회_테스트() {
        // given
        missionRepository.save(id_없는_미션_생성());
        missionProgressJpaRepository.save(
            MissionProgress.of(
                DEFAULT_GOAL, DEFAULT_MISSION_ID, DEFAULT_REWARD, DEFAULT_CHALLENGER_ID));

        // when
        Optional<MissionProgress> progress = queryDslRepository.findByChallengeIdAndType(
            DEFAULT_MISSION_ID, DEFAULT_OPERATE_TYPE, DEFAULT_RESOURCE_TYPE);

        // then
        assertThat(progress).isNotEmpty();
    }

    @Test
    void 도전자가_이미_미션을_수행하고_있는지_조회_테스트() {
        // given
        missionProgressJpaRepository.save(
            MissionProgress.of(
                DEFAULT_GOAL, DEFAULT_MISSION_ID, DEFAULT_REWARD, DEFAULT_CHALLENGER_ID));

        // when & then
        assertSoftly(softly -> {
            softly.assertThat(queryDslRepository.hasAlreadyChallengedMission(
                    DEFAULT_CHALLENGER_ID, DEFAULT_MISSION_ID))
                .isTrue();
            softly.assertThat(queryDslRepository.hasAlreadyChallengedMission(
                    2L, 2L))
                .isFalse();
        });
    }

    @Test
    void 미션_전체_조회_테스트() {
        // given
        Mission firstMission = id_없는_미션_생성();
        Mission secondMission = id_없는_미션_생성();
        Mission thirdMission = id_없는_미션_생성();

        missionRepository.save(firstMission);
        missionRepository.save(secondMission);
        missionRepository.save(thirdMission);
        missionProgressJpaRepository.save(
            MissionProgress.of(
                DEFAULT_GOAL, DEFAULT_MISSION_ID, DEFAULT_REWARD, DEFAULT_CHALLENGER_ID));
        missionProgressJpaRepository.save(
            MissionProgress.of(DEFAULT_GOAL, 2L, DEFAULT_REWARD, DEFAULT_CHALLENGER_ID));

        // when
        List<CurrentProgress> progress = queryDslRepository.findAll(
            DEFAULT_CHALLENGER_ID, null, null);

        // then
        assertThat(progress.size()).isEqualTo(2);
    }

    @Test
    void 완료된_미션_조회_테스트() {
        // given
        missionRepository.save(id_없는_미션_생성());
        missionProgressJpaRepository.save(id_없는_완료된_진행도_생성());

        // when
        List<CurrentProgress> progress = queryDslRepository.findAll(
            DEFAULT_CHALLENGER_ID, COMPLETED, null);

        // then
        assertThat(progress.isEmpty()).isFalse();
    }

    @Test
    void 진행중인_미션_조회_테스트() {
        // given
        missionRepository.save(id_없는_미션_생성());
        missionProgressJpaRepository.save(id_없는_진행중인_진행도_생성());

        // when
        List<CurrentProgress> progress = queryDslRepository.findAll(
            DEFAULT_CHALLENGER_ID, IN_PROGRESS, null);

        // then
        assertThat(progress.isEmpty()).isFalse();
    }

    @Test
    void 보상_수령_가능한_미션_조회_테스트() {
        // given
        missionRepository.save(id_없는_미션_생성());
        missionProgressJpaRepository.save(id_없는_보상_수령_가능한_진행도_생성());

        // when
        List<CurrentProgress> progress = queryDslRepository.findAll(
            DEFAULT_CHALLENGER_ID, null, WAITING);

        // then
        assertThat(progress.isEmpty()).isFalse();
    }

    @Test
    void 보상_수령한_미션_조회_테스트() {
        // given
        missionRepository.save(id_없는_미션_생성());
        missionProgressJpaRepository.save(id_없는_보상_수령한_진행도_생성());

        // when
        List<CurrentProgress> progress = queryDslRepository.findAll(
            DEFAULT_CHALLENGER_ID, null, RECEIVED);

        // then
        assertThat(progress.isEmpty()).isFalse();
    }

    @Test
    void 보상_수령_가능한_모든_보상_수령_테스트() {
        // given
        missionRepository.save(id_없는_미션_생성());
        missionProgressJpaRepository.save(id_없는_보상_수령_가능한_진행도_생성());
        missionRepository.save(id_없는_미션_생성());
        missionProgressJpaRepository.save(id_없는_보상_수령_가능한_진행도_생성());
        missionRepository.save(id_없는_미션_생성());
        missionProgressJpaRepository.save(id_없는_진행중인_진행도_생성());

        // when
        List<MissionProgress> waitingProgress = queryDslRepository.findRewardWaitingProgress(
            DEFAULT_CHALLENGER_ID);

        // then
        assertSoftly(softly -> {
            softly.assertThat(waitingProgress.size()).isEqualTo(2);
            softly.assertThat(waitingProgress.get(0).getId()).isEqualTo(1L);
            softly.assertThat(waitingProgress.get(1).getId()).isEqualTo(2L);
        });
    }
}
