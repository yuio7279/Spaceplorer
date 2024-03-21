package com.spaceplorer.spaceplorerweb.common;

//SSR에서 에러핸들링 하는 클래스

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 권한이 없음 401 에러, 메시지 출력 후 로그인 페이지로 이동
    @ExceptionHandler(value = UnAuthorizedException.class)
    public String handleUnAuthorizedException() {
        String errorMessage = URLEncoder.encode("로그인이 필요합니다.\n\n로그인 후 이용해주세요.", StandardCharsets.UTF_8);
        return "redirect:/errors/401?error="+errorMessage;
    }
    // 404 에러 페이지로
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException() {
        String errorMessage = URLEncoder.encode("페이지를 찾을 수 없습니다.", StandardCharsets.UTF_8);
        return "redirect:/errors/404?error="+errorMessage;
    }

    // 금지됨, 403 에러 페이지로
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException() {
        String errorMessage = URLEncoder.encode("접근 권한이 없습니다.", StandardCharsets.UTF_8);

        return "redirect:/errors/403?error="+errorMessage;
    }

    //그 외의 처리
    @ExceptionHandler(value = ResponseStatusException.class)
    public String handleDefaultException(ResponseStatusException e) {
        HttpStatusCode code = e.getStatusCode();
        String errorMessage = URLEncoder.encode(Objects.requireNonNull(e.getReason()), StandardCharsets.UTF_8);
        return "redirect:/errors/"+code.value()+"?error="+errorMessage;
    }
}


