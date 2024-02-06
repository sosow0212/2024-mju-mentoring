package com.mju.mentoring.member.controller;

import static com.mju.mentoring.member.controller.helper.CookieHelper.generateCookieByMemberProperties;

import com.mju.mentoring.member.controller.dto.SignupResponse;
import com.mju.mentoring.member.controller.dto.TokenResponse;
import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.service.AuthService;
import com.mju.mentoring.member.service.dto.AuthRequest;
import com.mju.mentoring.member.service.dto.LoginRequest;
import com.mju.mentoring.member.service.dto.SignupRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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

    private static final String SESSION_KEY = "member";
    private static final int SESSION_EXPIRE_SECONDS = 3600;

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid final SignupRequest request) {
        Member newMember = authService.signup(request);
        Long memberId = newMember.getId();
        SignupResponse response = new SignupResponse(memberId);

        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping("/login/cookie")
    public ResponseEntity<Void> loginWithCookie(@RequestBody @Valid final LoginRequest request,
                                                  final HttpServletResponse response) {
        Member loginMember = authService.nonJwtLogin(request);

        Cookie loginCookie = generateCookieByMemberProperties(loginMember.getNickname(), loginMember.getPassword());
        response.addCookie(loginCookie);

        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/login/session")
    public ResponseEntity<Void> loginWithSession(@RequestBody @Valid final LoginRequest request,
                                                 final HttpServletRequest httpRequest) {
        Member loginMember = authService.nonJwtLogin(request);
        HttpSession session = httpRequest.getSession();
        saveSessionProperties(session, loginMember);

        return ResponseEntity.ok()
                .build();
    }

    private static void saveSessionProperties(final HttpSession session, final Member loginMember) {
        AuthRequest authRequest = new AuthRequest(loginMember.getNickname(), loginMember.getPassword());
        session.setAttribute(SESSION_KEY, authRequest);
        session.setMaxInactiveInterval(SESSION_EXPIRE_SECONDS);
    }

    @PostMapping("/login/jwt")
    public ResponseEntity<TokenResponse> loginWithJwt(@RequestBody @Valid final LoginRequest request) {
        String token = authService.jwtLogin(request);
        return ResponseEntity.ok()
                .body(new TokenResponse(token));
    }
}
