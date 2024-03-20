package com.spaceplorer.spaceplorerweb.controller;

import com.spaceplorer.spaceplorerweb.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String login(){

        return "login";
    }

    @GetMapping("/login/success")
    public String loginSuccess(){
        return "redirect:/";
    }
    @GetMapping("/login/fail")
    public String loginFail(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "로그인이 실패하였습니다. 재 로그인 해주세요");
        return "redirect:/";
    }


    @PostMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        authService.logout(request, response);
        redirectAttributes.addFlashAttribute("logoutMsg", "로그아웃이 완료 되었습니다.");

        return "redirect:/";
    }


    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/errors")
    public String handleError(@RequestParam("message") String message, Model model) {
        model.addAttribute("errorMessage", message);
        return "errorPage"; // 에러 페이지로 이동
    }
}
