package com.mju.mentoring.board.ui;

import static com.mju.mentoring.board.fixture.BoardFixture.게시글_생성;
import static com.mju.mentoring.global.CustomRestDocsHandler.customDocument;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.mentoring.board.application.dto.BoardCreateRequest;
import com.mju.mentoring.board.application.dto.BoardDeleteRequest;
import com.mju.mentoring.board.application.dto.BoardUpdateRequest;
import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.global.BaseControllerWebMvcTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

class BoardControllerWebMvcTest extends BaseControllerWebMvcTest {

    private static final Long DEFAULT_WRITER_ID = 1L;
    private static final Long DEFAULT_BOARD_ID = 1L;
    private static final String HEADER_NAME = "Authorization";
    private static final String TOKEN_FORMAT = "Bearer accessToken...";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void 게시글_저장() throws Exception {
        // given
        BoardCreateRequest request = new BoardCreateRequest("title", "content");
        Board board = 게시글_생성();

        given(boardService.save(DEFAULT_WRITER_ID, request))
            .willReturn(board.getId());

        // when & then
        mockMvc.perform(post("/boards")
                .header(HEADER_NAME, TOKEN_FORMAT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andDo(customDocument("save-board",
                requestHeaders(
                    headerWithName("Authorization").description("액세스 토큰")
                ),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("본문")
                )));
    }

    @Test
    void 토큰_없이_게시글_조회() throws Exception {
        // given
        List<Board> response = List.of(게시글_생성());
        given(boardService.findAll())
            .willReturn(response);

        // when & then
        mockMvc.perform(get("/boards"))
            .andExpect(status().isOk())
            .andDo(customDocument("read-boards",
                responseFields(
                    fieldWithPath("boardsResponse").type(JsonFieldType.ARRAY)
                        .description("모든 게시글 배열"),
                    fieldWithPath("boardsResponse[].boardId").type(JsonFieldType.NUMBER)
                        .description("게시글의 id"),
                    fieldWithPath("boardsResponse[].writer").type(JsonFieldType.STRING)
                        .description("게시글의 작성자"),
                    fieldWithPath("boardsResponse[].title").type(JsonFieldType.STRING)
                        .description("게시글의 제목"),
                    fieldWithPath("boardsResponse[].content").type(JsonFieldType.STRING)
                        .description("게시글의 본문"),
                    fieldWithPath("boardsResponse[].view").type(JsonFieldType.NUMBER)
                        .description("게시글의 조회수")
                )
            ));
    }

    @Test
    void 토큰_없이_게시글_id로_조회() throws Exception {
        // given
        Board response = 게시글_생성();
        given(boardService.readBoard(1L))
            .willReturn(response);

        // when & then
        mockMvc.perform(get("/boards/{id}", DEFAULT_BOARD_ID))
            .andExpect(status().isOk())
            .andDo(customDocument("read-board",
                pathParameters(
                    parameterWithName("id").description("게시글의 id")
                ),
                responseFields(
                    fieldWithPath("boardId").type(JsonFieldType.NUMBER)
                        .description("게시글의 id"),
                    fieldWithPath("writer").type(JsonFieldType.STRING)
                        .description("게시글의 작성자"),
                    fieldWithPath("title").type(JsonFieldType.STRING)
                        .description("게시글의 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING)
                        .description("게시글의 본문"),
                    fieldWithPath("view").type(JsonFieldType.NUMBER)
                        .description("게시글의 조회수")
                )));
    }

    @Test
    void 게시글_업데이트() throws Exception {
        // given
        BoardUpdateRequest request = new BoardUpdateRequest("update title", "update content");

        // when & then
        mockMvc.perform(put("/boards/{id}", DEFAULT_BOARD_ID)
                .header(HEADER_NAME, TOKEN_FORMAT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            ).andExpect(status().isOk())
            .andDo(customDocument("update-board",
                pathParameters(
                    parameterWithName("id").description("게시글의 id")
                ),
                requestHeaders(
                    headerWithName("Authorization").description("액세스 토큰")
                ),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING)
                        .description("업데이트할 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING)
                        .description("업데이트할 본문")
                )));
    }

    @Test
    void 게시글_단건_삭제() throws Exception {
        // when & then
        mockMvc.perform(delete("/boards/{id}", DEFAULT_BOARD_ID)
                .header(HEADER_NAME, TOKEN_FORMAT))
            .andExpect(status().isOk())
            .andDo(customDocument("delete-board",
                pathParameters(
                    parameterWithName("id").description("게시글의 id")
                ),
                requestHeaders(
                    headerWithName("Authorization").description("액세스 토큰")
                )));
        ;
    }

    @Test
    void 게시글_여러건_삭제() throws Exception {
        // given
        BoardDeleteRequest request = new BoardDeleteRequest(List.of(1L));

        // when & then
        mockMvc.perform(delete("/boards")
                .header(HEADER_NAME, TOKEN_FORMAT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(customDocument("delete-boards",
                requestHeaders(
                    headerWithName("Authorization").description("액세스 토큰")
                ),
                requestFields(
                    fieldWithPath("ids").type(JsonFieldType.ARRAY)
                        .description("삭제할 게시글들의 id")
                )));
    }
}
