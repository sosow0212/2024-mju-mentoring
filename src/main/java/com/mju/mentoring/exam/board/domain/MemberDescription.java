package com.mju.mentoring.exam.board.domain;

import com.mju.mentoring.exam.board.exception.BadCredentialsException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MemberDescription {

	@Column(nullable = false, unique = true, name = "login_id")
	private String loginId;

	@Column(nullable = false)
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

	public void isValidPassword(String password) throws BadCredentialsException {
		if (!this.getPassword().equals(password)) {
			throw new BadCredentialsException();
		}
	}
}
