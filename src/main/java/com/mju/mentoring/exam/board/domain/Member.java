package com.mju.mentoring.exam.board.domain;

import java.nio.file.AccessDeniedException;

import com.mju.mentoring.exam.board.exception.BadCredentialsException;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	public void isValidPassword(String password) throws BadCredentialsException {
		this.memberDescription.isValidPassword(password);
	}

	public Member(MemberDescription memberDescription) {
		this.memberDescription = memberDescription;
	}

	public void writerValidation(Member member) throws AccessDeniedException {
		if (member.id != id) {
			throw new AccessDeniedException("작성자가 아닙니다");
		}
	}
}
