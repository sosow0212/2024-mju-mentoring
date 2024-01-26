package com.mju.mentoring.board.controller;

import com.mju.mentoring.board.dto.BoardDto;
import com.mju.mentoring.board.service.BoardService;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*; //필요한 개별 클래스만 import 하기
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

//Controller : 스프링 MVC 중 컨트롤러 역할, Client가 요청을 하면 그 요청을 실질적으로 수행하는 서비스를 호출함
//@Controller // 컨트롤러 역할을 한다는 것을 나타냄, 클라이언트의 요청을 처리하고 응답을 반환하는 메소드를 포함하고 있음
@RestController
//@controller를 사용하면 뷰 템플릿을 이용하여 HTML을 반환할 수 있지만 우리는 주로 데이터만 반환하기 때문에 @RestController의 사용이 더 효과적이다.
@RequestMapping("/board") //기본 URL 경로 /board
public class BoardController {

    //생성자 및 의존성 주입
    private final BoardService boardService;

    //@Autowired // 생성자 기반 의존성 주입 (Constructor-based Dependency Injection), 생성자가 하나이기 때문에 @Authwired 생략 가능
    // 클래스의 불변성을 보장, 의존성이 명확하게 드러남, 테스트하기 쉬움.
    //BoardService의 인스턴스를 BoardController에 자동으로 주입
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    /*@Autowired// 필드 기반 의존성 주입 (Field-based Dependency Injection)
    //코드가 간결하다는 장점, 클래스의 불변성을 보장할 수 없음, 테스트하기 어려움, 의존성이 숨겨져 있어 클래스의 구조를 이해하기 어려울 수 있음.
    private BoardService boardService;*/

    //C(Create) 게시글 생성기능
    @PostMapping// /board 경로로 들어오는 POST 요청 처리 // @RequestBody를 사용하여 클라이언트에서 받은 Body 부분 인자로 전달
    public ResponseEntity<BoardDto> createBoard(@RequestBody BoardDto boardDto) {
        BoardDto boardCreate = boardService.save(boardDto);
        return ResponseEntity.ok(boardCreate); //게시글 저장 및 HTTP 상태코드 200과 함께 게시글 객체 반환
    }

    // R(Read) 게시글 조회 기능
    @GetMapping// /board 경로로 들어오는 GET 요청 처리
    public ResponseEntity<List<BoardDto>> getAllBoards() {
        List<BoardDto> boardDtos = boardService.findAll();
        return ResponseEntity.ok(boardDtos); //findAll()을 통해 조회된 게시글과 HTTP 상태코드 200 반환
        // 자원이 생성되었을 때 -> '201 Created: 요청이 성공적으로 처리되었으며 새로운 리소스가 생성되었음을 나타냅니다.'
    }

    //U(Update) 게시글 수정 기능
    @PutMapping("/{id}")// /board/{id} 경로로 들어오는 PUT 요청 처리 // @PathVariable을 사용하여 {id}를 인자로 전달 // PUT과 MATCH 메서드의 차이점
    public ResponseEntity<BoardDto> updateBoards(@PathVariable Long id, @RequestBody BoardDto boardDetails) {
        BoardDto updatedBoardDto = boardService.update(id, boardDetails); //게시글 update
        return ResponseEntity.ok(updatedBoardDto); //코드의 가독성을 높이기 위해서 메서드 체이닝은 한 줄에 하나씩만 사용한다. //HTTP 상태토드 200 반환
    }

    //D(Delete) 게시글 삭제 기능
    @DeleteMapping("/{id}")// /board/{id} 경로로 들어오는 DELETE 요청 처리
    public ResponseEntity<Void> deleteBoards(@PathVariable Long id) {
        boardService.delete(id); //게시글 delete
        return ResponseEntity.noContent() //항상 204 반환이 상황에 따라 200 또는 데이터를 반환해줄 수도 있다.
                .build(); //삭제요청이 성공적으로 진행되었을 떄, 콘텐츠가 없다는 상태코드 204 반환(noContent)
    }
}
