package com.mju.mentoring.board.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.mentoring.exam.board.controller.MemberController;
import com.mju.mentoring.exam.board.domain.Member;
import com.mju.mentoring.exam.board.domain.MemberRepository;
import com.mju.mentoring.exam.board.service.dto.LoginRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static com.mju.mentoring.board.fixture.MemberFixture.멤버_생성;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    /**
     * 1.'컨트롤러'만 통합테스트 (슬라이스)
     * 문서화도 같이 진행하는 경우 많음
     *
     * 2. E2E 테스트 (모든레이어 거쳐감)
     */

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberRepository memberRepository;

    @Test
    void 로그인한다() throws Exception {
        // given
        Member member = 멤버_생성();
        memberRepository.save(member);
        LoginRequest req = new LoginRequest(member.getMemberDescription().getMemberId(), member.getMemberDescription().getPassword());

        // when & then
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        ).andExpect(status().isOk());
    }
}
