package com.mju.mentoring;

import com.mju.mentoring.exam.board.domain.Board;
import com.mju.mentoring.exam.board.infrastructure.BoardRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RepositoryTests {
    @Autowired
    BoardRepositoryImpl boardRepository;

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
        assertThat(board.isEmpty());
    }
    @Test
    void putTest() {
        String title = "test title";
        String content = "test content";
        Board newBoard = new Board(title,content);
        boardRepository.save(newBoard);
        Optional<Board> board = boardRepository.findById(1L);
        assertThat(board.get().getTitle()).isEqualTo(newBoard.getTitle());
        assertThat(board.get().getContent()).isEqualTo(newBoard.getContent());
    }
    @Test
    void postTest() {
        String title = "test title";
        String content = "test content";
        Board newBoard = new Board(title,content);
        boardRepository.save(newBoard);
        List<Board> boards = boardRepository.findAll();
        Board board = boards.get(0);

        assertThat(board.getTitle()).isEqualTo(newBoard.getTitle());
        assertThat(board.getContent()).isEqualTo(newBoard.getContent());
    }

}
