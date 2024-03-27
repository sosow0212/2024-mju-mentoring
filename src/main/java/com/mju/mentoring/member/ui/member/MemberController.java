package com.mju.mentoring.member.ui.member;

import com.mju.mentoring.member.application.member.MemberService;
import com.mju.mentoring.member.application.member.dto.ChangeNickNameRequest;
import com.mju.mentoring.member.ui.auth.support.AuthInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PatchMapping
    public ResponseEntity<Void> changeNickName(
        @AuthInformation final Long memberId, @RequestBody ChangeNickNameRequest request) {
        memberService.changeNickName(memberId, request);
        return ResponseEntity.ok()
            .build();
    }
}
