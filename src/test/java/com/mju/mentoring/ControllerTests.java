package com.mju.mentoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.mentoring.exam.board.domain.Board;
import com.mju.mentoring.exam.board.infrastructure.BoardRepositoryImpl;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.List;

@SpringBootTest
class ControllerTests {
    @Autowired
    BoardRepositoryImpl boardRepository;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void updateTest() throws Exception {
        String title = "test title";
        String content = "test content";
        Board newBoard = new Board(title,content);
        String posturl = "http://localhost:8080/boards";
        mvc.perform(post(posturl).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(newBoard)));
        String newtitle = "updated title";
        String newcontent = "updated content";
        Board updateBoard = new Board(newtitle,newcontent);
        String puturl = "http://localhost:8080/boards/1";

        mvc.perform(put(puturl).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(updateBoard)))
                .andExpect(status().is2xxSuccessful());

        List<Board> boards = boardRepository.findAll();
        Board board = boards.get(0);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(board.getTitle()).isEqualTo(updateBoard.getTitle());
        softAssertions.assertThat(board.getContent()).isEqualTo(updateBoard.getContent());
    }
    @Test
    void createTest() throws Exception {
        String title = "test title";
        String content = "test content";
        Board newBoard = new Board(title,content);
        String url = "http://localhost:8080/boards";

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(newBoard)))
                .andExpect(status().is2xxSuccessful());

        List<Board> boards = boardRepository.findAll();
        Board board = boards.get(0);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(board.getTitle()).isEqualTo(newBoard.getTitle());
        softAssertions.assertThat(board.getContent()).isEqualTo(newBoard.getContent());

    }
    @Test
    void deleteTest() throws Exception {
        String title = "test title";
        String content = "test content";
        Board newBoard = new Board(title,content);
        String url = "http://localhost:8080/boards";
        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(newBoard)))
                .andExpect(status().is2xxSuccessful());
        String deleteurl = "http://localhost:8080/boards/1";

        mvc.perform(RestDocumentationRequestBuilders.delete(deleteurl).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is2xxSuccessful());

        List<Board> boards = boardRepository.findAll();
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(boards.size()==0);
    }
    @Test
    void getTest() throws Exception {
//        String title = "test title";
//        String content = "test content";
//        Board newBoard = new Board(title,content);
//        String url = "http://localhost:8080/boards";
//        mvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .content(new ObjectMapper().writeValueAsString(newBoard)));
//        List<Board> boardList = boardRepository.findAll();
//
//        mvc.perform(RestDocumentationRequestBuilders.get("http://localhost:8080/boards/{id}",1L)
//                        .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().is2xxSuccessful());
//        assertThat(boardList.size()==1);
//
//
//        String getAllurl = "http://localhost:8080/boards";
//        ResultActions resultActions = mvc.perform(RestDocumentationRequestBuilders.get(getAllurl)
//                        .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().is2xxSuccessful());
//        assertThat(boardList.size()==1);
    }
}
