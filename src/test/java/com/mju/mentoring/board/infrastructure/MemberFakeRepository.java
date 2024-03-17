package com.mju.mentoring.board.infrastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.mju.mentoring.exam.board.domain.Member;
import com.mju.mentoring.exam.board.domain.MemberRepository;

public class MemberFakeRepository implements MemberRepository {

	private final Map<Long, Member> map = new HashMap<>();
	private final Map<String, Member> mapForId = new HashMap<>();

	private Long id = 1L;

	@Override
	public Member save(final Member member) {
		Member saved = Member.builder()
			.id(id)
			.memberDescription(member.getMemberDescription())
			.build();

		map.put(id, saved);
		mapForId.put(saved.getMemberDescription().getLoginId(), saved);
		id++;
		return saved;
	}

	@Override
	public Optional<Member> findById(final Long id) {
		return map.keySet().stream()
			.filter(key -> key.equals(id))
			.findAny()
			.map(map::get);
	}

	@Override
	public void deleteById(final Long id) {
		if (map.containsKey(id)) {
			map.remove(id);
		}
	}

	@Override
	public Optional<Member> findByLoginId(final String memberId) {
		return Optional.ofNullable(mapForId.get(memberId));
	}
}
