package com.mju.mentoring.mission.application.mission;

import com.mju.mentoring.global.event.Events;
import com.mju.mentoring.mission.domain.mission.ChallengedMissionEvent;
import com.mju.mentoring.mission.domain.mission.Mission;
import com.mju.mentoring.mission.domain.mission.MissionRepository;
import com.mju.mentoring.mission.exception.exceptions.NotFoundMissionException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;

    public List<Mission> findAll() {
        return missionRepository.findAll();
    }

    @Transactional
    public void challengeMission(final Long challengerId, final Long missionId) {
        missionRepository.findById(missionId)
            .ifPresentOrElse(mission -> Events.raise(
                    new ChallengedMissionEvent(
                        challengerId, missionId, mission.getGoal(), mission.getReward())),
                () -> {
                    throw new NotFoundMissionException(missionId);
                });
    }
}
