package com.mju.mentoring.member.controller;

import static com.mju.mentoring.member.controller.helper.CookieHelper.convertServletRequestToAuthRequest;

import com.mju.mentoring.member.controller.dto.ProfileResponse;
import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.service.MemberService;
import com.mju.mentoring.member.service.dto.AuthRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/profile/cookie")
    public ResponseEntity<ProfileResponse> profile(final HttpServletRequest request) {
        AuthRequest authRequest = convertServletRequestToAuthRequest(request);
        Member profileMember = memberService.getProfile(authRequest);

        ProfileResponse response = new ProfileResponse(profileMember.getNickname(), profileMember.getUsername());
        return ResponseEntity.ok()
                .body(response);
    }
}
