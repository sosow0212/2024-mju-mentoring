package com.mju.mentoring.board.fixture;

import com.mju.mentoring.exam.board.domain.Member;
import com.mju.mentoring.exam.board.domain.MemberDescription;

public class MemberFixture {
	public static Member 멤버_생성() {
		return Member.builder()
			.id(1L)
			.memberDescription(new MemberDescription("user1", "justperson", "password1", "nick"))
			.build();
	}
}
