package com.mju.mentoring.board.controller;

import static com.mju.mentoring.board.fixture.BoardFixtures.게시글_id_없음;
import static com.mju.mentoring.board.fixture.BoardFixtures.게시글_id_있음;

import static com.mju.mentoring.member.fixture.MemberFixtures.회원_id_있음;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.exception.exceptions.BoardNotFoundException;
import com.mju.mentoring.board.service.BoardService;
import com.mju.mentoring.board.service.dto.BoardCreateRequest;
import com.mju.mentoring.board.service.dto.BoardTextUpdateRequest;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.support.auth.AuthMemberResolver;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.stream.Stream;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(BoardController.class)
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoardService boardService;

    @MockBean
    private AuthMemberResolver authMemberResolver;

    @Test
    void 게시글을_저장한다() throws Exception {
        // given
        String createTitle = "title";
        String createContent = "content";

        Member member = 회원_id_있음();
        BoardCreateRequest request = new BoardCreateRequest(createTitle, createContent);
        Board newBoard = 게시글_id_있음();
        when(authMemberResolver.supportsParameter(any())).thenReturn(true);
        when(authMemberResolver.resolveArgument(any(), any(), any(), any())).thenReturn(member);
        when(boardService.save(request, member)).thenReturn(newBoard.getId());

        // when & then
        mockMvc.perform(post("/boards")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(newBoard.getId()))
                .andDo(print());

        verify(boardService).save(request, member);

    }

    @Test
    void 게시글을_id로_조회한다() throws Exception {
        // given
        Board newBoard = 게시글_id_있음();
        Long boardId = newBoard.getId();

        when(boardService.searchById(boardId)).thenReturn(newBoard);

        // when & then
        mockMvc.perform(get("/boards/{id}", boardId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(boardId))
                .andExpect(jsonPath("$.title").value(newBoard.getTitle()))
                .andExpect(jsonPath("$.content").value(newBoard.getContent()))
                .andDo(print());
    }

    @Test
    void 게시글을_전부_조회한다() throws Exception {
        // given
        List<Board> boards = List.of(게시글_id_없음(), 게시글_id_없음(), 게시글_id_없음(), 게시글_id_없음());

        when(boardService.findAll()).thenReturn(boards);

        // when & then
        mockMvc.perform(get("/boards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].title").value(게시글_id_없음().getTitle()))
                .andExpect(jsonPath("$[0].content").value(게시글_id_없음().getContent()))
                .andDo(print());
    }

    @Test
    void 게시글을_수정한다() throws Exception {
        // given
        String editTitle = "title (edited)";
        String editContent = "content (edited)";
        BoardTextUpdateRequest request = new BoardTextUpdateRequest(editTitle, editContent);
        Board savedBoard = 게시글_id_있음();
        Long boardId = savedBoard.getId();

        // when & then
        mockMvc.perform(patch("/boards/{id}", boardId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(jsonPath("$.id").value(savedBoard.getId()))
                .andDo(print());

        verify(boardService).updateText(boardId, request);
    }

    @Test
    void 게시글을_삭제한다() throws Exception {
        // given
        Board savedBoard = 게시글_id_있음();
        Long boardId = savedBoard.getId();

        // when & then
        mockMvc.perform(delete("/boards/{id}", boardId)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(boardService).deleteById(boardId);
    }

    @Nested
    class 예외_테스트 {

        @ParameterizedTest(name = "제목이 [{0}], 내용이 [{1}]인 경우")
        @MethodSource("exceptionTitleAndContent")
        void 저장할_게시글의_제목과_내용이_null_이거나_비어있으면_안된다(final String title, final String content) throws Exception {
            // given
            BoardCreateRequest request = new BoardCreateRequest(title, content);

            // when & then
            mockMvc.perform(post("/boards")
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").exists())
                    .andDo(print());
        }

        private static Stream<String[]> exceptionTitleAndContent() {
            return Stream.of(
                    new String[]{"title", ""},
                    new String[]{"", "content"},
                    new String[]{"", ""},
                    new String[]{"title", null},
                    new String[]{null, "content"},
                    new String[]{null, null}
            );
        }

        @ParameterizedTest(name = "제목이 [{0}], 내용이 [{1}]인 경우")
        @MethodSource("exceptionTitleAndContent")
        void 수정할_게시글의_제목과_내용이_null_이거나_비어있으면_안된다(final String title, final String content) throws Exception {
            // given
            BoardTextUpdateRequest request = new BoardTextUpdateRequest(title, content);
            Board savedBoard = 게시글_id_있음();
            Long boardId = savedBoard.getId();

            // when & then
            mockMvc.perform(patch("/boards/{id}", boardId)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").exists())
                    .andDo(print());
        }

        @Test
        void id에_맞는_게시글이_없으면_예외가_발생한다() throws Exception {
            // given
            Long searchId = 100L;
            when(boardService.searchById(searchId)).thenThrow(BoardNotFoundException.class);

            // when & then
            mockMvc.perform(get("/boards/{id}", searchId))
                    .andExpect(result ->
                            assertInstanceOf(BoardNotFoundException.class, result.getResolvedException())
                    )
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
    }
}
