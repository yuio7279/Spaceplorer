package com.spaceplorer.spaceplorerweb.auth.social;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

//인증 실패 등을 처리하기 위함
@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // 에러 메시지를 쿼리 파라미터나 세션으로 전달
        // 여기서는 쿼리 파라미터를 사용하는 예시입니다.
        String errorMessage = "로그인 후 이용해주세요.";
        log.info("[:{}]",authException.toString());
        String encodedMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
        response.sendRedirect("/errors?message=" + encodedMessage);
    }
}
