package com.mju.mentoring.board.contorller;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//Controller : 웹 MVC의 컨트롤러 역할, Client가 요청을 하면 그 요청을 실질적으로 수행하는 서비스를 호출한다.
@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;
    @GetMapping("/board/create")
    public String boardCreate(){

        System.out.println(" 글 작성 페이지로 이동 ");
        return "boardwrite";
    }

    @PostMapping("/board/save")
    public String boardWrite(Board board){
        boardService.save(board);

        return " ";
    }
}
