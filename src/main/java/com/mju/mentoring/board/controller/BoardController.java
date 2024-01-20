package com.mju.mentoring.board.controller;

import com.mju.mentoring.board.controller.dto.BoardCreateResponse;
import com.mju.mentoring.board.controller.dto.BoardSearchResponse;
import com.mju.mentoring.board.controller.dto.BoardUpdateResponse;
import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.service.BoardService;
import com.mju.mentoring.board.service.dto.BoardCreateRequest;
import com.mju.mentoring.board.service.dto.BoardTextUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/boards")
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardCreateResponse> create(@RequestBody final BoardCreateRequest request) {
        Long newBoardId = boardService.save(request);
        BoardCreateResponse response = new BoardCreateResponse(newBoardId);

        URI newBoardURI = URI.create("/boards/" + newBoardId);

        return ResponseEntity.created(newBoardURI)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardSearchResponse> findById(@PathVariable final Long id) {
        Board findBoard = boardService.searchById(id);
        BoardSearchResponse response = new BoardSearchResponse(
                findBoard.getId(),
                findBoard.getTitle(),
                findBoard.getContent()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardUpdateResponse> updateText(@PathVariable Long id,
                                                          @RequestBody final BoardTextUpdateRequest request) {
        boardService.updateText(id, request);
        BoardUpdateResponse response = new BoardUpdateResponse(id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable final Long id) {
        boardService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
