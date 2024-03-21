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
import org.springframework.web.bind.annotation.PathVariable;
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


/*    @GetMapping("/test/{error_code}")
    public void test(@PathVariable(value = "error_code") String code) {
        switch (code) {
            case "401" -> throw new UnAuthorizedException();
            case "403" -> throw new AccessDeniedException("접근 권한이 없습니다.");
            case "404" -> throw new NoSuchElementException();
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제발좀 되라");
        }
    }*/

    //에러핸들링
    @GetMapping("/errors/{error_code}")
    public String handleError(
            Model model,
            @RequestParam("error") String errorMessage,
            @PathVariable(value = "error_code") String code) {

        model.addAttribute("errorMessage",errorMessage);

        return switch (code) {
            case "401" -> "error/error_401";
            case "403" -> "error/error_403";
            case "404" -> "error/error_404";
            default -> "error/error";
        };
    }
}
