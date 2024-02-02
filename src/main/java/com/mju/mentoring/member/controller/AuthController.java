package com.mju.mentoring.member.controller;

import static com.mju.mentoring.member.controller.helper.CookieHelper.generateCookieByMemberProperties;

import com.mju.mentoring.member.controller.dto.SignupResponse;
import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.service.AuthService;
import com.mju.mentoring.member.service.dto.LoginRequest;
import com.mju.mentoring.member.service.dto.SignupRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody final SignupRequest request) {
        Member newMember = authService.signup(request);
        Long memberId = newMember.getId();
        SignupResponse response = new SignupResponse(memberId);

        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping("/login/cookie")
    public ResponseEntity<Void> loginWithCookie(@RequestBody final LoginRequest request,
                                                  final HttpServletResponse response) {
        Member loginMember = authService.login(request);

        Cookie loginCookie = generateCookieByMemberProperties(loginMember.getUsername(), loginMember.getPassword());
        response.addCookie(loginCookie);

        return ResponseEntity.ok()
                .build();
    }
}
