package com.mju.mentoring.member.ui.member;

import static com.mju.mentoring.global.CustomRestDocsHandler.customDocument;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.mentoring.global.BaseControllerWebMvcTest;
import com.mju.mentoring.member.application.member.dto.ChangeNickNameRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

class MemberControllerTest extends BaseControllerWebMvcTest {

    private static final Long MEMBER_DEFAULT_ID = 1L;
    private static final String HEADER_NAME = "Authorization";
    private static final String TOKEN_FORMAT = "Bearer accessToken...";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void 닉네임_수정_테스트() throws Exception {
        // given
        ChangeNickNameRequest request = new ChangeNickNameRequest("new nickname");
        willDoNothing().given(memberService).changeNickName(MEMBER_DEFAULT_ID, request);

        // when & then
        mockMvc.perform(patch("/members/nickname")
                .header(HEADER_NAME, TOKEN_FORMAT)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(customDocument("change nickname",
                requestFields(
                    fieldWithPath("newNickname").type(JsonFieldType.STRING).description("새로운 닉네임")
                )));
    }

}
