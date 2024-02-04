package com.mju.mentoring.member.controller;

import static com.mju.mentoring.member.controller.helper.CookieHelper.convertServletRequestToAuthRequest;

import com.mju.mentoring.member.controller.dto.ProfileResponse;
import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.service.MemberService;
import com.mju.mentoring.member.service.dto.AuthRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

    private static final String SESSION_KEY = "member";

    private final MemberService memberService;

    @GetMapping("/profile/cookie")
    public ResponseEntity<ProfileResponse> profile(final HttpServletRequest request) {
        AuthRequest authRequest = convertServletRequestToAuthRequest(request);
        Member profileMember = memberService.getProfileWithAuthRequest(authRequest);

        ProfileResponse response = new ProfileResponse(profileMember.getNickname(), profileMember.getUsername());
        return ResponseEntity.ok()
                .body(response);
    }
    
    @GetMapping("/profile/session")
    public ResponseEntity<ProfileResponse> profileWithSession(final HttpServletRequest request) {
        HttpSession session = request.getSession();
        AuthRequest authRequest = (AuthRequest) session.getAttribute(SESSION_KEY);
        Member profileMember = memberService.getProfileWithAuthRequest(authRequest);

        ProfileResponse response = new ProfileResponse(profileMember.getNickname(), profileMember.getUsername());
        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/profile/jwt")
    public ResponseEntity<ProfileResponse> profileWithJwt(@RequestHeader("Authorization") final String token) {
        Member profileMember = memberService.getProfileWithJwt(token);

        ProfileResponse response = new ProfileResponse(profileMember.getNickname(), profileMember.getUsername());
        return ResponseEntity.ok()
                .body(response);
    }
}
