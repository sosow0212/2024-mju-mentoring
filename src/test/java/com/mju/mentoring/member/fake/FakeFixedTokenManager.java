package com.mju.mentoring.member.fake;

import com.mju.mentoring.member.domain.TokenManager;

public class FakeFixedTokenManager implements TokenManager<Long> {

    private static final Long FIXED_RESPONSE = 1L;

    @Override
    public String create(final Long id) {
        return String.valueOf(FIXED_RESPONSE);
    }

    @Override
    public Long parse(final String token) {
        return FIXED_RESPONSE;
    }
}
