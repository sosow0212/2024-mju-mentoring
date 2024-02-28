package com.mju.mentoring.exam.board.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Embedded
    private MemberDescription memberDescription;

    public boolean isValidPassword(String password) {
        return memberDescription.isValidPassword(password);
    }

    public Member(MemberDescription memberDescription) {
        this.memberDescription = memberDescription;
    }

    public Member(String memberId, String username, String password, String nickname) {
        this.memberDescription = new MemberDescription(memberId, username, password, nickname);
    }
}
