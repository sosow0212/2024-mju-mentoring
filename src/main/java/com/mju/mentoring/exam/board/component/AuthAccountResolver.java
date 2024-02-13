package com.mju.mentoring.exam.board.component;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.mju.mentoring.exam.board.domain.AuthAccount;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthAccountResolver implements HandlerMethodArgumentResolver {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasAnnotation = parameter.hasParameterAnnotation(AuthAccount.class);
		boolean isAccountType = Long.class.isAssignableFrom(parameter.getParameterType());
		return hasAnnotation & isAccountType;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

		String token = httpServletRequest.getHeader("Authorization");
		if (jwtTokenProvider.validateToken(token)) {
			return jwtTokenProvider.getIdFromToken(token);
		}
		throw new RuntimeException("권한 없음");
	}

}
