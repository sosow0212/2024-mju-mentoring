package com.mju.mentoring.exam.board.controller;

import com.mju.mentoring.exam.board.controller.dto.BoardResponse;
import com.mju.mentoring.exam.board.domain.Board;
import com.mju.mentoring.exam.board.service.BoardService;
import com.mju.mentoring.exam.board.service.dto.BoardCreateRequest;
import com.mju.mentoring.exam.board.service.dto.BoardUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    @Autowired
    private final BoardService boardService;

    @PostMapping("/boards")
    public ResponseEntity<Void> save(@RequestBody final BoardCreateRequest boardCreateRequest) {
        Long boardId = boardService.save(boardCreateRequest);
        return ResponseEntity.created(URI.create("/boards/" + boardId))
                .build();
    }

    @GetMapping("/boards")
    public ResponseEntity<List<BoardResponse>> findAll() {
        List<BoardResponse> response = boardService.findAll().stream()
                .map(BoardResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<BoardResponse> findById(@PathVariable("id") final Long id) {
        Board board = boardService.findById(id);
        return ResponseEntity.ok(BoardResponse.from(board));
    }

    @PutMapping("/boards/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") final Long id,@RequestBody final BoardUpdateRequest request) {
        boardService.update(id, request);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") final Long id) {
        boardService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }
}
