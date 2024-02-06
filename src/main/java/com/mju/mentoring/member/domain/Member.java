package com.mju.mentoring.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // 아이디
    private String password; // 비밀번호
    private String name; // 이름

    public Member(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }
}
