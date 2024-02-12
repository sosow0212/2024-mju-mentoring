package com.mju.mentoring.member.controller;

import com.mju.mentoring.member.controller.dto.ProfileResponse;
import com.mju.mentoring.member.support.auth.AuthMember;
import com.mju.mentoring.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getProfile(@AuthMember Member member) {
        ProfileResponse response = new ProfileResponse(member.getNickname(), member.getUsername());
        return ResponseEntity.ok()
                .body(response);
    }
}
