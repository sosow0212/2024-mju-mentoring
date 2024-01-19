package com.mju.mentoring.board.controller;

import com.mju.mentoring.board.controller.dto.BoardResponse;
import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.service.BoardService;
import com.mju.mentoring.board.service.dto.BoardCreateRequest;
import com.mju.mentoring.board.service.dto.BoardUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {

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
    public ResponseEntity<BoardResponse> findById(@PathVariable final Long id) {
        Board board = boardService.findById(id);

        return ResponseEntity.ok(BoardResponse.from(board));
    }

    @PatchMapping("/boards/{id}")
    public ResponseEntity<Void> update(@PathVariable final Long id, final BoardUpdateRequest request) {
        boardService.update(id, request);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable final Long id) {
        boardService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }
}
