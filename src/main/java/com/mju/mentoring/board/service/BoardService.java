package com.mju.mentoring.board.service;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.dto.BoardCreateRequest;
import com.mju.mentoring.board.dto.BoardListResponse;
import com.mju.mentoring.board.dto.BoardResponse;
import com.mju.mentoring.board.dto.BoardUpdateRequest;
import com.mju.mentoring.board.exception.CustomException;
import com.mju.mentoring.board.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//Serice : 핵심 비즈니스 로직을 구현, 주로 리포지토리를 이용해 CRUD을 구현함, 레포지토리와 컨트롤러 사이 로직 처리
@Service
public class BoardService {

    //생성자 및 의존성 주입
    private final BoardRepository boardRepository;

    //@Autowired // 생성자 기반 의존성 주입 (Constructor-based Dependency Injection), 생성자가 하나이기 때문에 @Authwired 생략 가능
    // 클래스의 불변성을 보장, 의존성이 명확하게 드러남, 테스트하기 쉬움.
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /*@Autowired // 필드 기반 의존성 주입 (Field-based Dependency Injection)
    //코드가 간결하다는 장점, 클래스의 불변성을 보장할 수 없음, 테스트하기 어려움, 의존성이 숨겨져 있어 클래스의 구조를 이해하기 어려울 수 있음.
    private BoardRepository boardRepository;*/

    @Transactional //데이터베이스와 작업을 수행할 때 사용
    public BoardResponse createBoard(BoardCreateRequest createRequest) {
        Board board = new Board(createRequest.title(), createRequest.content());
        //Board board = new Board();
        //board.setTitle(boardDto.getTitle()); // boardDto의 제목을 가져와서 board에 설정
        //board.setContent(boardDto.getContent()); // boardDto의 내용을 가져와서 board에 설정
        Board savedBoard = boardRepository.save(board); // board를 DB에 저장
        return new BoardResponse(savedBoard.getId(), savedBoard.getBoardContent().getTitle(), savedBoard.getBoardContent().getContent()); //return BoardDto.fromBoard(savedBoard); 정적팩토리 메서드 사용 // new BoardDto(savedBoard.getId(), savedBoard.getBoardContent().getTitle(), savedBoard.getBoardContent().getContent()); // BoardDto 생성
    }

    @Transactional(readOnly = true)
    public List<BoardListResponse> findAll() {
        return boardRepository.findAll().stream()
                .map(board -> new BoardListResponse(board.getId(), board.getBoardContent().getTitle())) //정적팩토리 메서드 사용 //board -> new BoardDto(board.getId(), board.getBoardContent().getTitle(), board.getBoardContent().getTitle())) //Board에서 각각 BoardDto로 변환
                .collect(Collectors.toList()); //리스트로 반환
    }

    @Transactional
    public BoardResponse updateBoard(Long id, BoardUpdateRequest updateRequest) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(id)); //게시글을 찾을 수 없는 경우 CustomExeption 발생
        //board.setTitle(boardDetails.getTitle()); //title 업데이트
        //board.setContent(boardDetails.getContent()); //content 업데이트
        board.update(updateRequest.title(), updateRequest.content()); //도메인 객체의 update 메서드를 이용한 데이터 업데이트
        Board updateBoard = boardRepository.save(board); //업데이트된 게시글을 DB에 저장
        return new BoardResponse(updateBoard.getId(), updateBoard.getBoardContent().getTitle(), updateBoard.getBoardContent().getContent()); //return BoardDto.fromBoard(updateBoard); 정적팩토리 메서드 사용 //new BoardDto(updateBoard.getId(), updateBoard.getBoardContent().getTitle(), updateBoard.getBoardContent().getContent()); //BoardDto 생성
    }

    @Transactional
    public void deleteBoard(Long id) {
        if (!boardRepository.existsById(id)) { //existsById를 활용하여 주어진 아이디가 DB에 있는지 확인(JpaRepository에 포함된 메소드)
            throw new CustomException(id); //게시글을 찾을 수 없는 경우 CustomExeption 발생
        }
        boardRepository.deleteById(id); //해당 id의 게시글 삭제
    }
}
