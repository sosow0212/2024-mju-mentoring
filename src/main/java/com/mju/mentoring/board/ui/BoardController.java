package com.mju.mentoring.board.ui;

import com.mju.mentoring.board.ui.dto.BoardResponse;
import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.application.BoardService;
import com.mju.mentoring.board.application.dto.BoardUpdateRequest;
import com.mju.mentoring.board.application.dto.BoardCreateRequest;
import java.net.URI;
import java.util.List;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody final BoardCreateRequest boardCreateRequest) {
        Long createdBardId = boardService.save(boardCreateRequest);

        return ResponseEntity.created(URI.create("/boards/" + createdBardId))
            .build();
    }

    @GetMapping
    public ResponseEntity<List<BoardResponse>> findAll() {
        List<BoardResponse> boardResponses = boardService.findAll().stream()
            .map(BoardResponse::from)
            .toList();

        return ResponseEntity.ok(boardResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> findBoardById(@PathVariable(name = "id") final Long id) {
        Board board = boardService.findById(id);

        return ResponseEntity.ok(BoardResponse.from(board));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateById(@PathVariable(name = "id") final Long id,
        @RequestBody BoardUpdateRequest boardUpdateRequest) {
        boardService.update(id, boardUpdateRequest);

        return ResponseEntity.ok()
            .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id") final Long id) {
        boardService.deleteById(id);

        return ResponseEntity.ok()
            .build();
    }
}