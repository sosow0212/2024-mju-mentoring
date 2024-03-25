package com.mju.mentoring.member.fake;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.domain.MemberRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeMemberRepository implements MemberRepository {

    Map<Long, Member> db = new HashMap<>();
    private Long id = 1L;

    @Override
    public void save(final Member member) {
        db.put(id++, member);
    }

    @Override
    public boolean existsByUsername(final String username) {
        return db.keySet()
            .stream()
            .anyMatch(key -> db.get(key).getUsername().equals(username));
    }

    @Override
    public boolean existsByNickname(final String nickname) {
        return db.keySet()
            .stream()
            .anyMatch(key -> db.get(key).getNickname().equals(nickname));
    }

    @Override
    public Optional<Member> findByUsername(final String username) {
        return db.keySet()
            .stream()
            .filter(key -> db.get(key).getUsername().equals(username))
            .map(key -> db.get(key))
            .findAny();
    }

    @Override
    public Optional<Member> findById(final Long id) {
        return db.keySet()
            .stream()
            .filter(key -> db.get(key).getId().equals(id))
            .map(key -> db.get(key))
            .findAny();
    }
}
