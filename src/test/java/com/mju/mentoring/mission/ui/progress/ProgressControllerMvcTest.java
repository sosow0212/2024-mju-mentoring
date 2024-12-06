package com.mju.mentoring.mission.ui.progress;

import static com.mju.mentoring.mission.domain.progress.ProgressStatus.COMPLETED;
import static com.mju.mentoring.mission.domain.progress.RewardStatus.WAITING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.mentoring.global.BaseControllerWebMvcTest;
import com.mju.mentoring.mission.domain.progress.ProgressStatus;
import com.mju.mentoring.mission.domain.progress.RewardStatus;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class ProgressControllerMvcTest extends BaseControllerWebMvcTest {

    private static final String HEADER_NAME = "Authorization";
    private static final String TOKEN_FORMAT = "Bearer accessToken...";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<ProgressStatus> progressStatusArgumentCaptor;

    @Captor
    ArgumentCaptor<RewardStatus> rewardStatusArgumentCaptor;

    @Test
    void 진행도_전체_조회_미션_상태_테스트() throws Exception {
        // given
        given(progressService.findAll(
            any(), progressStatusArgumentCaptor.capture(), any()))
            .willReturn(List.of());

        // when
        mockMvc.perform(get("/progress?progressStatus=COMPLETED")
                .header(HEADER_NAME, TOKEN_FORMAT)
            )
            .andExpect(status().isOk());

        // then
        assertThat(progressStatusArgumentCaptor.getValue()).isEqualTo(COMPLETED);
    }

    @Test
    void 진행도_전체_조회_보상_상태_테스트() throws Exception {
        // given
        given(progressService.findAll(
            any(), any(), rewardStatusArgumentCaptor.capture()))
            .willReturn(List.of());

        // when
        mockMvc.perform(get("/progress?rewardStatus=WAITING")
                .header(HEADER_NAME, TOKEN_FORMAT)
            )
            .andExpect(status().isOk());

        // then
        assertThat(rewardStatusArgumentCaptor.getValue()).isEqualTo(WAITING);
    }

    @Test
    void reward_status_변환_실패_테스트() throws Exception {
        // when & then
        mockMvc.perform(get("/progress?rewardStatus=OTHER")
                .header(HEADER_NAME, TOKEN_FORMAT)
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    void progress_status_변환_실패_테스트() throws Exception {
        // when & then
        mockMvc.perform(get("/progress?progressStatus=OTHER")
                .header(HEADER_NAME, TOKEN_FORMAT)
            )
            .andExpect(status().isBadRequest());
    }
}
