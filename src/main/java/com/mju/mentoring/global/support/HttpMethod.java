package com.mju.mentoring.global.support;

public enum HttpMethod {

    GET,
    HEAD,
    POST,
    PUT,
    PATCH,
    DELETE,
    CONNECT,
    OPTION,
    TRACE,
    ANY;

    public boolean isMatches(final String method) {
        return this.name().equals(method);
    }
}
