package com.mju.mentoring.member.ui.auth.support;

import com.mju.mentoring.member.domain.TokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String HEADER_NAME = "Authorization";

    private final TokenManager<Long> tokenManager;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthInformation.class)
            && parameter.getGenericParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
        final ModelAndViewContainer mavContainer,
        final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory)
        throws Exception {
        String authorization = webRequest.getHeader(HEADER_NAME);
        String token = TokenExtractor.extractToken(authorization);
        return tokenManager.parse(token);
    }
}
