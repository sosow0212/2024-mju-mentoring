package com.mju.mentoring.member.ui.auth;

import com.mju.mentoring.member.application.auth.AuthService;
import com.mju.mentoring.member.application.auth.dto.ChangeNickNameRequest;
import com.mju.mentoring.member.application.auth.dto.SignInRequest;
import com.mju.mentoring.member.application.auth.dto.SignupRequest;
import com.mju.mentoring.member.ui.auth.dto.TokenResponse;
import com.mju.mentoring.member.ui.auth.support.AuthInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignupRequest request) {
        authService.signup(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> signIn(@RequestBody SignInRequest request) {
        TokenResponse response = authService.signIn(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/nickname")
    public ResponseEntity<Void> changeNickName(@AuthInformation final Long memberId,
        @RequestBody ChangeNickNameRequest request) {
        authService.changeNickName(memberId, request);
        return ResponseEntity.ok()
            .build();
    }
}
