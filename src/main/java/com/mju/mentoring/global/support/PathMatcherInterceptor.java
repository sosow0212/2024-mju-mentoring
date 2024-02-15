package com.mju.mentoring.global.support;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@AllArgsConstructor
public class PathMatcherInterceptor implements HandlerInterceptor {

    private final HandlerInterceptor handlerInterceptor;
    private final PathContainer pathContainer;

    public static PathMatcherInterceptor from(final HandlerInterceptor handlerInterceptor) {
        return new PathMatcherInterceptor(handlerInterceptor, PathContainer.createDefault());
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
        final Object handler) throws Exception {
        if (pathContainer.notIncludedPath(request.getServletPath(), request.getMethod())) {
            return true;
        }

        return handlerInterceptor.preHandle(request, response, handler);
    }

    public PathMatcherInterceptor includePathPattern(final String pathPattern,
        final HttpMethod method) {
        pathContainer.includePathPattern(pathPattern, method);
        return this;
    }

    public PathMatcherInterceptor excludePathPattern(final String pathPattern,
        final HttpMethod method) {
        pathContainer.excludePathPattern(pathPattern, method);
        return this;
    }
}
