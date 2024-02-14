package com.mju.mentoring.exam.board.domain;

import com.mju.mentoring.exam.board.exception.MemberNotFoundException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MemberDescription {

	@Column(nullable = false, unique = true, name = "login_Id")
	private String loginId;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String nickname;

	public MemberDescription(String loginId, String username, String password, String nickname) {
		this.loginId = loginId;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
	}

	public boolean isValidPassword(String password) {
		if (!this.getPassword().equals(password)) {
			throw new MemberNotFoundException("비밀번호가 일치하지 않습니다");
		}
		return true;
	}
}
