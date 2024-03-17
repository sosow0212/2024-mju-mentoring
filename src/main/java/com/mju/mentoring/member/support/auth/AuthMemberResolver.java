package com.mju.mentoring.member.support.auth;

import com.mju.mentoring.member.domain.Member;
import com.mju.mentoring.member.exception.exceptions.AuthorizationException;
import com.mju.mentoring.member.service.MemberService;
import com.mju.mentoring.member.support.auth.handler.AuthMemberHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthMemberResolver implements HandlerMethodArgumentResolver {

    private final Map<String, AuthMemberHandler> authMemberHandlers = new HashMap<>();
    private final MemberService memberService;

    public AuthMemberResolver(final ApplicationContext applicationContext, final MemberService memberService) {
        authMemberHandlers.putAll(applicationContext.getBeansOfType(AuthMemberHandler.class));
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMember.class);
    }

    @Override
    public Member resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory) {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        AuthMemberHandler authHandler = authMemberHandlers.values()
                .stream()
                .filter(handler -> handler.handleRequest(request))
                .findFirst()
                .orElseThrow(AuthorizationException::new);

        return authHandler.extractMember(memberService, request);
    }
}
