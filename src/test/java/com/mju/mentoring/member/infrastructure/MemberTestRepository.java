package com.mju.mentoring.member.infrastructure;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.domain.MemberAuth;
import com.mju.mentoring.member.domain.MemberRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemberTestRepository implements MemberRepository {

    private final Map<Long, Member> store = new HashMap<>();

    private Long id = 1L;

    @Override
    public Member save(final Member member) {
        Member newMember = Member.builder()
                .id(id)
                .nickname(member.getNickname())
                .memberAuth(new MemberAuth(member.getUsername(), member.getPassword()))
                .build();

        store.put(id, newMember);
        id++;
        return newMember;
    }

    @Override
    public Optional<Member> findByUsername(final String username) {
        return store.values()
                .stream()
                .filter(member -> member.isSameUsername(username))
                .findFirst();
    }

    @Override
    public void deleteById(final Long memberId) {
        store.remove(memberId);
    }
}
