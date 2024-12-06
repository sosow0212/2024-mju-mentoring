package com.mju.mentoring.mission.ui.progress;

import com.mju.mentoring.member.ui.auth.support.AuthInformation;
import com.mju.mentoring.mission.application.progress.ProgressService;
import com.mju.mentoring.mission.application.progress.dto.ProgressResponse;
import com.mju.mentoring.mission.domain.progress.ProgressStatus;
import com.mju.mentoring.mission.domain.progress.RewardStatus;
import com.mju.mentoring.mission.ui.progress.dto.ProgressResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/progress")
public class ProgressController {

    private final ProgressService progressService;

    @GetMapping
    public ResponseEntity<ProgressResponses> findAll(
        @AuthInformation final Long challengerId,
        @RequestParam(name = "progressStatus", required = false) final ProgressStatus progressStatus,
        @RequestParam(name = "rewardStatus", required = false) final RewardStatus rewardStatus
    ) {
        List<ProgressResponse> response = progressService.findAll(challengerId, progressStatus, rewardStatus);
        return ResponseEntity.ok(ProgressResponses.of(response));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> receiveReward(
        @AuthInformation final Long challengerId, @PathVariable("id") final Long id) {
        progressService.receiveReward(challengerId, id);
        return ResponseEntity.ok()
            .build();
    }

    @PostMapping
    public ResponseEntity<Void> receiveAllRewards(@AuthInformation final Long challengerId) {
        progressService.receiveAllRewards(challengerId);
        return ResponseEntity.ok()
            .build();
    }
}
