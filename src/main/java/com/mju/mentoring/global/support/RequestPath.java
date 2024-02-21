package com.mju.mentoring.global.support;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestPath {

    private final String pathPattern;
    private final HttpMethod method;

    public boolean matchesMethod(final String method) {
        return this.method.isMatches(method);
    }
}
