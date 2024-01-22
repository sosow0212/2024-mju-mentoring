package com.mju.mentoring;

import com.mju.mentoring.exam.board.domain.Board;
import com.mju.mentoring.exam.board.domain.BoardRepository;
import com.mju.mentoring.exam.board.infrastructure.BoardRepositoryImpl;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class BoardRepositoryTests {
    @Autowired
    BoardRepository boardRepository;

    @Test
    void getTest() {
    }
    @Test
    void deleteTest() {
        String title = "test title";
        String content = "test content";
        Board newBoard = new Board(title,content);
        boardRepository.save(newBoard);
        boardRepository.deleteById(1L);

        Optional<Board> board = boardRepository.findById(1L);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(board.isEmpty());
    }
    @Test
    void postTest() {
        String title = "test title";
        String content = "test content";
        Board newBoard = new Board(title,content);
        boardRepository.save(newBoard);

        List<Board> boards = boardRepository.findAll();

        Board board = boards.get(0);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(board.getTitle()).isEqualTo(newBoard.getTitle());
        softAssertions.assertThat(board.getContent()).isEqualTo(newBoard.getContent());
    }

}
