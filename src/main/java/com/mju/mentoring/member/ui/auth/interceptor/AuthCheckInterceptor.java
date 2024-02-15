package com.mju.mentoring.member.ui.auth.interceptor;

import com.mju.mentoring.member.domain.TokenManager;
import com.mju.mentoring.member.ui.auth.support.TokenExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthCheckInterceptor implements HandlerInterceptor {

    private static final String HEADER_NAME = "Authorization";

    private final TokenManager<Long> tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        Optional<String> authorization = Optional.ofNullable(request.getHeader(HEADER_NAME));
        String token = TokenExtractor.extractToken(authorization);
        tokenManager.parse(token);
        return true;
    }
}
