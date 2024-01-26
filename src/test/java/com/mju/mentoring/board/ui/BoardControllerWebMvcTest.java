package com.mju.mentoring.board.ui;

import static com.mju.mentoring.board.fixture.BoardFixture.게시글_생성;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.mentoring.board.application.BoardService;
import com.mju.mentoring.board.application.dto.BoardCreateRequest;
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
        BoardCreateRequest req = new BoardCreateRequest("title", "content");
        Board board = 게시글_생성();

        when(boardService.save(eq(req))).thenReturn(board.getId());

        // when & then
        mockMvc.perform(post("/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated());
    }

    @Test
    void 게시글_조회() throws Exception {
        // given
        List<Board> response = List.of(게시글_생성());
        when(boardService.findAll()).thenReturn(response);

        // when & then
        mockMvc.perform(get("/boards"))
            .andExpect(status().isOk());
    }

    @Test
    void 게시글_id로_조회() throws Exception {
        // given
        Board response = 게시글_생성();
        when(boardService.findById(eq(1L))).thenReturn(response);

        // when & then
        mockMvc.perform(get("/boards/1"))
            .andExpect(status().isOk());
    }
}
