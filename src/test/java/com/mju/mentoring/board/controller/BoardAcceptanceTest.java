package com.mju.mentoring.board.controller;

import static com.mju.mentoring.board.fixture.BoardFixture.게시글_생성;
import static com.mju.mentoring.board.fixture.BoardFixture.게시글_생성_id없음;
import static com.mju.mentoring.board.fixture.MemberFixture.멤버_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.mju.mentoring.board.infrastructure.DatabaseCleanup;
import com.mju.mentoring.exam.board.component.JwtTokenProvider;
import com.mju.mentoring.exam.board.domain.Board;
import com.mju.mentoring.exam.board.domain.BoardRepository;
import com.mju.mentoring.exam.board.domain.Member;
import com.mju.mentoring.exam.board.domain.MemberRepository;
import com.mju.mentoring.exam.board.service.dto.BoardCreateRequest;
import com.mju.mentoring.exam.board.service.dto.BoardUpdateRequest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BoardAcceptanceTest {
	@Autowired
	private DatabaseCleanup databaseCleanup;
	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private BoardRepository boardRepository;

	@BeforeEach
	public void setUp() {
		databaseCleanup.execute();
	}

	@Test
	void 게시글을_저장한다() {
		// given
		Member member = 멤버_생성();
		BoardCreateRequest request = new BoardCreateRequest("title", "content");
		Member savedMember = memberRepository.save(member);
		String memberToken = tokenProvider.createJwtAccessToken(savedMember);

		// when
		ExtractableResponse<Response> response = RestAssured.given().log().all()
			.header(HttpHeaders.AUTHORIZATION, memberToken)
			.contentType(ContentType.JSON)
			.body(request)
			.when()
			.post("/boards")
			.then().log().all()
			.extract();
		// then
		assertSoftly(softly -> {
			softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
			softly.assertThat(response.header("location")).isEqualTo("/boards/1");
		});
	}

	@Test
	void 게시글을_조회한다() {
		// given
		Member member = 멤버_생성();
		memberRepository.save(member);
		Board board = boardRepository.save(게시글_생성(member)); // 게시글을 저장하는 메서드를 호출하여 게시글을 저장합니다.
		String memberToken = tokenProvider.createJwtAccessToken(member);

		// when
		ExtractableResponse<Response> response = RestAssured.given().log().all()
			.header(HttpHeaders.AUTHORIZATION, memberToken)
			.when()
			.get("/boards/{boardId}", board.getId()) // 저장된 게시글의 ID를 사용하여 게시글을 불러옵니다.
			.then().log().all()
			.extract();

		// then
		assertSoftly(softly -> {
			softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
			softly.assertThat(response.body().jsonPath().getString("title"))
				.isEqualTo(board.getBoardDescription().getTitle());
			softly.assertThat(response.body().jsonPath().getString("content"))
				.isEqualTo(board.getBoardDescription().getContent());
			softly.assertThat(response.body().jsonPath().getLong("id"))
				.isEqualTo(board.getId());
			softly.assertThat(response.body().jsonPath().getLong("views"))
				.isEqualTo(1L);
		});
	}

	@Test
	void 게시글을_동시에_조회한다() throws InterruptedException {
		// given
		// 게시글 작성자와 게시글 정보를 설정합니다.
		Member member = 멤버_생성();
		memberRepository.save(member);
		Board board = 게시글_생성(member);
		boardRepository.save(board);

		// 게시글을 조회할 멤버 토큰 생성
		String memberToken = tokenProvider.createJwtAccessToken(member);
		AtomicInteger totalViews = new AtomicInteger();

		int numberOfThreads = 3;
		ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);
		for (int i = 0; i < numberOfThreads; i++) {
			service.execute(() -> {
				ExtractableResponse<Response> response = RestAssured.given().log().all()
					.header(HttpHeaders.AUTHORIZATION, memberToken)
					.when()
					.get("/boards/{boardId}", board.getId())
					.then().log().all()
					.extract();
				int views = response.body().jsonPath().getInt("views");
				totalViews.addAndGet(views);
				latch.countDown();
			});
		}
		latch.await();
		assertThat(totalViews.get()).isEqualTo(6);
	}

	@Test
	void 게시글을_전부_조회한다() {
		// given
		Member member = 멤버_생성();
		memberRepository.save(member);
		Board board = null;
		for (int i = 0; i < 5; i++) {
			board = boardRepository.save(게시글_생성_id없음(member));
		} // 게시글을 저장하는 메서드를 호출하여 게시글을 저장합니다.
		String memberToken = tokenProvider.createJwtAccessToken(member);

		// when
		ExtractableResponse<Response> response = RestAssured.given().log().all()
			.header(HttpHeaders.AUTHORIZATION, memberToken)
			.when()
			.get("/boards")
			.then().log().all()
			.extract();

		// then
		assertSoftly(softly -> {
			softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
			softly.assertThat(response.body().jsonPath().getList("$").size()).isEqualTo(5);
		});
	}

	@Test
	void 게시글을_업데이트한다() {
		// given
		Member member = 멤버_생성();
		memberRepository.save(member);
		BoardUpdateRequest request = new BoardUpdateRequest("updatedTitle", "updatedContent");
		Board board = boardRepository.save(게시글_생성(member));
		String memberToken = tokenProvider.createJwtAccessToken(member);

		// when
		ExtractableResponse<Response> response = RestAssured.given().log().all()
			.header(HttpHeaders.AUTHORIZATION, memberToken)
			.contentType(ContentType.JSON)
			.body(request)
			.when()
			.put("/boards/{id}", board.getId())
			.then().log().all()
			.extract();

		// then
		assertSoftly(softly -> {
			softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
		});
	}

	@Test
	void 게시글을_삭제한다() {
		// given
		Member member = 멤버_생성();
		memberRepository.save(member);
		Board board = boardRepository.save(게시글_생성(member));
		String memberToken = tokenProvider.createJwtAccessToken(member);

		// when
		ExtractableResponse<Response> response = RestAssured.given().log().all()
			.header(HttpHeaders.AUTHORIZATION, memberToken)
			.contentType(ContentType.JSON)
			.when()
			.delete("/boards/{id}", board.getId())
			.then().log().all()
			.extract();

		// then
		assertSoftly(softly -> {
			softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
		});
	}
}
