package com.mju.mentoring.board.controller;

import com.mju.mentoring.board.controller.dto.BoardCreateResponse;
import com.mju.mentoring.board.controller.dto.BoardSearchResponse;
import com.mju.mentoring.board.controller.dto.BoardUpdateResponse;
import com.mju.mentoring.board.domain.Board;
import com.mju.mentoring.board.service.BoardService;
import com.mju.mentoring.board.service.dto.BoardCreateRequest;
import com.mju.mentoring.board.service.dto.BoardTextUpdateRequest;
import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.support.auth.AuthMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/boards")
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardCreateResponse> create(@AuthMember final Member member, @RequestBody @Valid final BoardCreateRequest request) {
        Long newBoardId = boardService.save(request, member);
        BoardCreateResponse response = new BoardCreateResponse(newBoardId);

        URI newBoardURI = URI.create("/boards/" + newBoardId);

        return ResponseEntity.created(newBoardURI)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardSearchResponse> findById(@PathVariable final Long id) {
        Board findBoard = boardService.searchById(id);
        BoardSearchResponse response = BoardSearchResponse.from(findBoard);

        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<BoardSearchResponse>> findAll() {
        List<Board> allBoards = boardService.findAll();
        List<BoardSearchResponse> responses = allBoards.stream()
                .map(BoardSearchResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BoardUpdateResponse> updateText(@PathVariable final Long id,
                                                          @RequestBody @Valid final BoardTextUpdateRequest request) {
        boardService.updateText(id, request);
        BoardUpdateResponse response = new BoardUpdateResponse(id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable final Long id) {
        boardService.deleteById(id);

        return ResponseEntity.noContent()
                .build();
    }
}
