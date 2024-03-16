package com.spaceplorer.spaceplorerweb.common;

//SSR에서 에러핸들링 하는 클래스

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ResponseStatusException.class)
    public String handleIllegalArgumentException(ResponseStatusException e, Model model) {
        log.debug("[exception occurred:{}]",e.getReason());
        model.addAttribute("responseStatusException", e);
        return "errorPage"; // 에러 페이지로의 뷰 이름
    }
}
