package com.mju.mentoring.board.ui;

import static com.mju.mentoring.board.fixture.BoardFixture.게시글_생성;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.mentoring.board.application.BoardService;
import com.mju.mentoring.board.application.dto.BoardCreateRequest;
import com.mju.mentoring.board.application.dto.BoardDeleteRequest;
import com.mju.mentoring.board.application.dto.BoardUpdateRequest;
import com.mju.mentoring.board.domain.Board;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(BoardController.class)
class BoardControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoardService boardService;

    @Test
    void 게시글_저장() throws Exception {
        // given
        BoardCreateRequest request = new BoardCreateRequest("title", "content");
        Board board = 게시글_생성();

        given(boardService.save(request))
            .willReturn(board.getId());

        // when & then
        mockMvc.perform(post("/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());
    }

    @Test
    void 게시글_조회() throws Exception {
        // given
        List<Board> response = List.of(게시글_생성());
        given(boardService.findAll())
            .willReturn(response);

        // when & then
        mockMvc.perform(get("/boards"))
            .andExpect(status().isOk());
    }

    @Test
    void 게시글_id로_조회() throws Exception {
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
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk());
    }

    @Test
    void 게시글_단건_삭제() throws Exception {
        // when & then
        mockMvc.perform(delete("/boards/1"))
            .andExpect(status().isOk());
    }

    @Test
    void 게시글_여러건_삭제() throws Exception {
        // given
        BoardDeleteRequest request = new BoardDeleteRequest(List.of(1L));

        // when & then
        mockMvc.perform(delete("/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }
}
