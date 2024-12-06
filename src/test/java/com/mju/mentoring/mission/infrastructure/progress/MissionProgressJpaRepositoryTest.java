package com.mju.mentoring.mission.infrastructure.progress;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.mju.mentoring.global.DatabaseCleaner;
import com.mju.mentoring.mission.domain.progress.MissionProgress;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DatabaseCleaner
class MissionProgressJpaRepositoryTest {

    private static final Long DEFAULT_GOAL = 1L;
    private static final Long DEFAULT_MISSION_ID = 1L;
    private static final Long DEFAULT_CHALLENGE_ID = 1L;
    private static final Long DEFAULT_REWARD = 1000L;

    @Autowired
    private MissionProgressJpaRepository missionProgressJpaRepository;

    @Test
    void 미션_id와_도전자_id로_진행도_조회_테스트() {
        // given
        missionProgressJpaRepository.save(
            MissionProgress.of(DEFAULT_GOAL, DEFAULT_MISSION_ID, DEFAULT_REWARD, DEFAULT_CHALLENGE_ID));

        // when
        Optional<MissionProgress> progress = missionProgressJpaRepository.findByMissionIdAndChallengerId(
            1L, 1L);

        // then
        assertSoftly(softly -> {
            softly.assertThat(progress).isNotEmpty();
            softly.assertThat(progress.get().getChallengerId()).isEqualTo(1L);
            softly.assertThat(progress.get().getMissionId()).isEqualTo(1L);
        });
    }

}
