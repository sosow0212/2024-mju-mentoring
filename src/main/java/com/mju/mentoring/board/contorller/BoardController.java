package com.mju.mentoring.board.contorller;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controller : 스프링 MVC 중 컨트롤러 역할, Client가 요청을 하면 그 요청을 실질적으로 수행하는 서비스를 호출함
@Controller // 컨트롤러 역할을 한다는 것을 나타냄, 클라이언트의 요청을 처리하고 응답을 반환하는 메소드를 포함하고 있음
@RequestMapping("/board") //기본 URL 경로 /board
public class BoardController {

    //생성자 및 의존성 주입
    private final BoardService boardService;

    @Autowired // 생성자 기반 의존성 주입 (Constructor-based Dependency Injection) 클래스의 불변성을 보장, 의존성이 명확하게 드러남, 테스트하기 쉬움.
    //BoardService의 인스턴스를 BoardController에 자동으로 주입
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    /*@Autowired// 필드 기반 의존성 주입 (Field-based Dependency Injection)
    //코드가 간결하다는 장점, 클래스의 불변성을 보장할 수 없음, 테스트하기 어려움, 의존성이 숨겨져 있어 클래스의 구조를 이해하기 어려울 수 있음.
    private BoardService boardService;*/

    //C(Create) 게시글 생성기능
    @PostMapping// /board 경로로 들어오는 POST 요청 처리 // @RequestBody를 사용하여 클라이언트에서 받은 Body 부분 인자로 전달
    public ResponseEntity<Board> createBoard(@RequestBody Board board) {
        return ResponseEntity.ok(boardService.save(board)); //게시글 저장 및 HTTP 상태코드 200과 함께 게시글 객체 반환
    }

    // R(Read) 게시글 조회 기능
    @GetMapping// /board 경로로 들어오는 GET 요청 처리
    public ResponseEntity<List<Board>> getAllBoards() {
        return ResponseEntity.ok(boardService.findAll()); //findAll()을 통해 조회된 게시글과 HTTP 상태코드 200 반환
    }

    //U(Update) 게시글 수정 기능
    @PutMapping("/{id}")// /board/{id} 경로로 들어오는 PUT 요청 처리 // @PathVariable을 사용하여 {id}를 인자로 전달
    public ResponseEntity<Board> updateBoards(@PathVariable Long id, @RequestBody Board boardDetails) {
        boardService.update(id, boardDetails); //게시글 update
        return ResponseEntity.ok().build(); //HTTP 상태토드 200 반환
    }

    //D(Delete) 게시글 삭제 기능
    @DeleteMapping("/{id}")// /board/{id} 경로로 들어오는 DELETE 요청 처리
    public ResponseEntity<Board> deleteBoards(@PathVariable Long id) {
        boardService.delete(id); //게시글 delete
        return ResponseEntity.noContent().build(); //삭제요청이 성공적으로 진행되었을 떄, 콘텐츠가 없다는 상태코드 204 반환(noContent)
    }
}
