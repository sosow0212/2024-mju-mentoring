package com.mju.mentoring.member.config;

import com.mju.mentoring.global.support.HttpMethod;
import com.mju.mentoring.global.support.PathMatcherInterceptor;
import com.mju.mentoring.member.ui.auth.interceptor.AuthCheckInterceptor;
import com.mju.mentoring.member.ui.auth.support.AuthArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AuthConfig implements WebMvcConfigurer {

    private static final String ALL_URL_PATTERN = "/**";
    private static final String AUTH_URL_PATTERN = "/auth/**";
    private static final String BOARD_URL_PATTERN = "/boards/**";

    private final AuthArgumentResolver authArgumentResolver;
    private final AuthCheckInterceptor authCheckInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(registerAuthCheckInterceptor())
            .addPathPatterns(ALL_URL_PATTERN);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
    }

    private HandlerInterceptor registerAuthCheckInterceptor() {
        PathMatcherInterceptor interceptor = PathMatcherInterceptor.from(authCheckInterceptor);
        return interceptor.excludePathPattern(ALL_URL_PATTERN, HttpMethod.OPTION)
            .excludePathPattern(AUTH_URL_PATTERN, HttpMethod.ANY)
            .excludePathPattern(BOARD_URL_PATTERN, HttpMethod.GET);
    }
}
