package com.mju.mentoring.exam.board.domain;

import jakarta.persistence.*;
import jdk.jshell.Snippet;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private MemberDescription memberDescription;

    public boolean validation(String password) {
        if(this.memberDescription.getPassword().equals(password)) return true;
        return false;
    }
}
