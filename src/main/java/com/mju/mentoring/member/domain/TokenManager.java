package com.mju.mentoring.member.domain;

public interface TokenManager<T> {

    String create(final T id);

    T parse(final String token);
}
