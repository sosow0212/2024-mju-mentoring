package com.mju.mentoring.exam.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mju.mentoring.exam.board.service.MemberService;
import com.mju.mentoring.exam.board.service.dto.LoginRequest;

import com.mju.mentoring.exam.board.service.dto.SignUpRequest;


import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
		String token = memberService.getLoginToken(loginRequest);
		return ResponseEntity.ok(token);
	}

	@PostMapping("/signup")
	public ResponseEntity<Void> signup(@RequestBody SignUpRequest signUpRequest) {
		memberService.save(signUpRequest);
		return ResponseEntity.ok().build();
	}
}
