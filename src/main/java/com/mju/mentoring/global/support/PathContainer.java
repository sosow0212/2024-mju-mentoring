package com.mju.mentoring.global.support;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PathContainer {

    private final PathMatcher pathMatcher;
    private final List<RequestPath> includePathPattern;
    private final List<RequestPath> excludePathPattern;

    public static PathContainer createDefault() {
        return new PathContainer(new AntPathMatcher(), new ArrayList<>(), new ArrayList<>());
    }

    public boolean notIncludedPath(final String pathPattern, final String method) {
        boolean isExclude = excludePathPattern.stream()
            .anyMatch(requestPath -> anyMatchPattern(pathPattern, method, requestPath));

        boolean isNotIncluded = includePathPattern.stream()
            .noneMatch(requestPath -> anyMatchPattern(pathPattern, method, requestPath));

        return isNotIncluded || isExclude;
    }

    private boolean anyMatchPattern(final String pathPattern, final String method,
        final RequestPath requestPath) {
        return pathMatcher.match(requestPath.getPathPattern(), pathPattern) &&
            requestPath.matchesMethod(method);
    }

    public void includePathPattern(final String pathPattern, final HttpMethod method) {
        includePathPattern.add(new RequestPath(pathPattern, method));
    }

    public void excludePathPattern(final String pathPattern, final HttpMethod method) {
        excludePathPattern.add(new RequestPath(pathPattern, method));
    }
}
