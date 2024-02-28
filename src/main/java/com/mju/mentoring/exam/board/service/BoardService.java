package com.mju.mentoring.exam.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mju.mentoring.exam.board.domain.Board;
import com.mju.mentoring.exam.board.domain.BoardRepository;
import com.mju.mentoring.exam.board.domain.Member;
import com.mju.mentoring.exam.board.domain.MemberRepository;
import com.mju.mentoring.exam.board.exception.BoardNotFoundException;
import com.mju.mentoring.exam.board.exception.MemberNotFoundException;
import com.mju.mentoring.exam.board.service.dto.BoardCreateRequest;
import com.mju.mentoring.exam.board.service.dto.BoardUpdateRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;
	private final MemberRepository memberRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public Long save(final long memberId, final BoardCreateRequest request) {
		Member member = this.memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException("id에 해당하는 member가 존재하지 않습니다"));
		Board board = new Board(request.title(), request.content(), member, 0L);
		Board savedBoard = boardRepository.save(board);
		return savedBoard.getId();
	}

	@Transactional(readOnly = true)
	public List<Board> findAll() {
		return boardRepository.findAll();
	}

	@Transactional
	public Board findById(final Long id) {
		Board board = findByIdOrThrowException(id);
		board.addView();
		return board;
	}

	@Transactional
	public void update(final Long memberId, final Long id, final BoardUpdateRequest request) {
		Member member = this.memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException("id에 해당하는 member가 존재하지 않습니다"));
		Board board = findByIdOrThrowException(id);
		if (board.writerValidation(member)) {
			board.update(request.title(), request.content());
		} else {
			throw new MemberNotFoundException("작성자가 아닙니다");
		}
	}

	@Transactional
	public void delete(final Long memberId, Long id) {
		Member member = this.memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException("id에 해당하는 member가 존재하지 않습니다"));
		Board board = findByIdOrThrowException(id);
		if (board.writerValidation(member)) {
			boardRepository.deleteById(id);
		} else {
			throw new MemberNotFoundException("작성자가 아닙니다");
		}
	}

	private Board findByIdOrThrowException(Long id) {
		return boardRepository.findById(id)
			.orElseThrow(() -> new BoardNotFoundException());
	}
}
