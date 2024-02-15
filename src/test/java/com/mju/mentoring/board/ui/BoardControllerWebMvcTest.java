package com.mju.mentoring.board.ui;

import static com.mju.mentoring.board.fixture.BoardFixture.게시글_생성;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.mentoring.board.application.dto.BoardCreateRequest;
import com.mju.mentoring.board.application.dto.BoardDeleteRequest;
import com.mju.mentoring.board.application.dto.BoardUpdateRequest;
import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.global.BaseControllerWebMvcTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class BoardControllerWebMvcTest extends BaseControllerWebMvcTest {

    private static final Long DEFAULT_WRITER_ID = 1L;
    private static final String HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_PREFIX = "Bearer ";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    void init() {
        token = tokenManager.create(DEFAULT_WRITER_ID);
    }

    @Test
    void 게시글_저장() throws Exception {
        // given
        BoardCreateRequest request = new BoardCreateRequest("title", "content");
        Board board = 게시글_생성();

        given(boardService.save(DEFAULT_WRITER_ID, request))
            .willReturn(board.getId());

        // when & then
        mockMvc.perform(post("/boards")
                .header(HEADER_NAME, AUTHORIZATION_PREFIX + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());
    }

    @Test
    void 토큰_없이_게시글_조회() throws Exception {
        // given
        List<Board> response = List.of(게시글_생성());
        given(boardService.findAll())
            .willReturn(response);

        // when & then
        mockMvc.perform(get("/boards"))
            .andExpect(status().isOk());
    }

    @Test
    void 토큰_없이_게시글_id로_조회() throws Exception {
        // given
        Board response = 게시글_생성();
        given(boardService.findById(1L))
            .willReturn(response);

        // when & then
        mockMvc.perform(get("/boards/1"))
            .andExpect(status().isOk());
    }

    @Test
    void 게시글_업데이트() throws Exception {
        // given
        BoardUpdateRequest request = new BoardUpdateRequest("update title", "update content");

        // when & then
        mockMvc.perform(put("/boards/1")
            .header(HEADER_NAME, AUTHORIZATION_PREFIX + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk());
    }

    @Test
    void 게시글_단건_삭제() throws Exception {
        // when & then
        mockMvc.perform(delete("/boards/1")
                .header(HEADER_NAME, AUTHORIZATION_PREFIX + token))
            .andExpect(status().isOk());
    }

    @Test
    void 게시글_여러건_삭제() throws Exception {
        // given
        BoardDeleteRequest request = new BoardDeleteRequest(List.of(1L));

        // when & then
        mockMvc.perform(delete("/boards")
                .header(HEADER_NAME, AUTHORIZATION_PREFIX + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }
}
