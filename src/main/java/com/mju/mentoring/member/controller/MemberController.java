package com.mju.mentoring.member.controller;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController // REST 컨트롤러로 선언
public class MemberController {

    private final MemberService memberService; // MemberService에 대한 의존성 주입

    public MemberController(MemberService memberService) { // 생성자를 통한 의존성 주입
        this.memberService = memberService;
    }

    @PostMapping("/login") // login 경로로 POST 요청이 오면 실행
    public ResponseEntity<String> login(@RequestBody Member loginMember, HttpServletResponse response) {
        Optional<Member> member = memberService.authenticate(loginMember.getUsername(), loginMember.getPassword());
        if (member.isPresent()) {
            // 로그인 성공 시, 쿠키에 사용자 정보 저장
            Cookie cookie = new Cookie("memberId", member.get().getId().toString()); // 쿠키를 생성하여 사용자 ID를 저장
            cookie.setHttpOnly(true); // 쿠키를 HttpOnly로 설정하여 JavaScript를 통한 접근을 방지
            response.addCookie(cookie); // 생성한 쿠키를 HTTP 응답에 추가
            return ResponseEntity.ok("Login success"); // 로그인 성공 메시지와 200 응답을 반환
        } else {
            return ResponseEntity.status(401).body("Login fail"); // 인증 실패 시 401 응답을 반환
        }
    }
}
