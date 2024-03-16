package com.spaceplorer.spaceplorerweb.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AuthController {

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
    public String logout(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("logoutMsg", "로그아웃이 완료 되었습니다.");
        return "redirect:/";
    }
}
