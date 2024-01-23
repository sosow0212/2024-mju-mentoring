package com.mju.mentoring.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.service.BoardService;
import com.mju.mentoring.board.service.dto.BoardCreateRequest;
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

import java.util.List;

import static com.mju.mentoring.board.fixture.BoardFixture.게시글_생성;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(BoardController.class)
class BoardControllerWebMvcTest {

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
    private BoardService boardService;

    @Test
    void 게시글을_저장한다() throws Exception {
        // given
        BoardCreateRequest req = new BoardCreateRequest("title", "content");
        Board savedBoard = 게시글_생성();

        when(boardService.save(eq(req))).thenReturn(savedBoard.getId());

        // when & then
        mockMvc.perform(post("/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        ).andExpect(status().isCreated());
    }

    @Test
    void 게시글을_조회한다() throws Exception {
        // given
        List<Board> response = List.of(게시글_생성());
        when(boardService.findAll()).thenReturn(response);

        // when & then
        mockMvc.perform(get("/boards"))
                .andExpect(status().isOk());
    }
}
