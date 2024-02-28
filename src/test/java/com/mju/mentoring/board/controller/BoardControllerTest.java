package com.mju.mentoring.board.controller;

import static com.mju.mentoring.board.fixture.BoardFixture.*;
import static com.mju.mentoring.board.fixture.MemberFixture.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.mentoring.exam.board.component.AuthAccountResolver;
import com.mju.mentoring.exam.board.component.JwtTokenProvider;
import com.mju.mentoring.exam.board.controller.BoardController;
import com.mju.mentoring.exam.board.domain.Board;
import com.mju.mentoring.exam.board.domain.Member;
import com.mju.mentoring.exam.board.service.BoardService;
import com.mju.mentoring.exam.board.service.dto.BoardCreateRequest;
import com.mju.mentoring.exam.board.service.dto.BoardUpdateRequest;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(BoardController.class)
@Import({JwtTokenProvider.class, AuthAccountResolver.class})
class BoardControllerTest {

	/**
	 * 1.'컨트롤러'만 통합테스트 (슬라이스)
	 * 문서화도 같이 진행하는 경우 많음
	 * <p>
	 * 2. E2E 테스트 (모든레이어 거쳐감)
	 */

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private BoardService boardService;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Test
	void 게시글을_저장한다() throws Exception {
		// given
		BoardCreateRequest req = new BoardCreateRequest("title", "content");
		String memberToken = jwtTokenProvider.createJwtAccessToken(멤버_생성());
		when(boardService.save(1L, req)).thenReturn(1L);

		// when & then
		mockMvc.perform(post("/boards")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(req))
			.header(HttpHeaders.AUTHORIZATION, memberToken)
		).andExpect(status().isCreated());
	}

	@Test
	void 게시글을_조회한다() throws Exception {
		// given
		Member member = 멤버_생성();
		Board response = 게시글_생성(member);
		when(boardService.findById(1L)).thenReturn(response);

		// when & then
		mockMvc.perform(get("/boards/1"))
			.andExpect(status().isOk());
	}

	@Test
	void 게시글을_전부_조회한다() throws Exception {
		// given
		Member member = 멤버_생성();
		List<Board> response = List.of(게시글_생성(member));
		when(boardService.findAll()).thenReturn(response);

		// when & then
		mockMvc.perform(get("/boards"))
			.andExpect(status().isOk());
	}

	@Test
	void 게시글을_변경한다() throws Exception {
		// given
		BoardUpdateRequest req = new BoardUpdateRequest("title", "content");
		String memberToken = jwtTokenProvider.createJwtAccessToken(멤버_생성());

		// when & then
		mockMvc.perform(put("/boards/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(req))
			.header(HttpHeaders.AUTHORIZATION, memberToken)
		).andExpect(status().isOk());

	}

	@Test
	void 게시글을_삭제한다() throws Exception {
		// given
		Member member = 멤버_생성();
		List<Board> response = List.of(게시글_생성(member));
		String memberToken = jwtTokenProvider.createJwtAccessToken(멤버_생성());

		when(boardService.findAll()).thenReturn(response);

		// when & then
		mockMvc.perform(get("/boards"))
			.andExpect(status().isOk());

		mockMvc.perform(delete("/boards/1")
			.contentType(MediaType.APPLICATION_JSON)
			.header(HttpHeaders.AUTHORIZATION, memberToken)
		).andExpect(status().isNoContent());

	}
}

