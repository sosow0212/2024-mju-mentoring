package com.mju.mentoring.board.service;

import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//핵심 비즈니스 로직을 구현, 주로 리포지토리를 이용해 CRUD을 구현한다.
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    //C(Create) 게시글 생성기능
    public void save(Board board){
        boardRepository.save(board);
    }

    //R(Read) 게시글 불러오기 기능

    //U(Update) 게시글 수정 기능

    //D(Delete) 게시글 삭제 기능


}
