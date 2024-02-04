package com.mju.mentoring.exam.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mju.mentoring.exam.board.service.MemberService;
import com.mju.mentoring.exam.board.service.dto.LoginRequest;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

	@Autowired
	private final MemberService memberService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
		System.out.println(loginRequest.memberId());
		System.out.println(loginRequest.password());
		String token = memberService.getLoginToken(loginRequest);
		return ResponseEntity.ok(token);
	}
}
