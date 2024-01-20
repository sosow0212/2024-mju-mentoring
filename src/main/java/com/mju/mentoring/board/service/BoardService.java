package com.mju.mentoring.board.service;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//Serice : 핵심 비즈니스 로직을 구현, 주로 리포지토리를 이용해 CRUD을 구현함, 레포지토리와 컨트롤러 사이 로직 처리
@Service
public class BoardService {

    //생성자 및 의존성 주입
    private final BoardRepository boardRepository;

    @Autowired // 생성자 기반 의존성 주입 (Constructor-based Dependency Injection)
    // 클래스의 불변성을 보장, 의존성이 명확하게 드러남, 테스트하기 쉬움.
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /*@Autowired // 필드 기반 의존성 주입 (Field-based Dependency Injection)
    //코드가 간결하다는 장점, 클래스의 불변성을 보장할 수 없음, 테스트하기 어려움, 의존성이 숨겨져 있어 클래스의 구조를 이해하기 어려울 수 있음.
    private BoardRepository boardRepository;*/

    public Board save(Board board){
        return boardRepository.save(board); //게시글 저장
    }

    public List<Board> findAll() {
        return boardRepository.findAll(); //게시글 조회
    }

    public void update(Long id, Board boardDetails) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. - " + id)); //게시글을 찾을 수 없는 경우 EntityNotFoundException 발생
        board.setTitle(boardDetails.getTitle()); //title 업데이트
        board.setContent(boardDetails.getContent()); //content 업데이트
        boardRepository.save(board);
    }
    public void delete(Long id) {
        if (!boardRepository.existsById(id)) { //existsById를 활용하여 주어진 아이디가 DB에 있는지 확인(JpaRepository에 포함된 메소드)
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다. - " + id);
        }
        boardRepository.deleteById(id); //해당 id의 게시글 삭제
    }

}
