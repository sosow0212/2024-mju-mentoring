package com.mju.mentoring.member.config;

import com.mju.mentoring.member.ui.auth.interceptor.AuthCheckInterceptor;
import com.mju.mentoring.member.ui.auth.support.AuthArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AuthConfig implements WebMvcConfigurer {

    private static final String BOARD_URL_PATTERN = "/boards/**";
    private static final String AUTH_URL_PATTERN = "/auth/**";

    private final AuthArgumentResolver authArgumentResolver;
    private final AuthCheckInterceptor authCheckInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authCheckInterceptor)
            .excludePathPatterns(AUTH_URL_PATTERN)
            .addPathPatterns(BOARD_URL_PATTERN);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
    }
}
