package com.spaceplorer.spaceplorerweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService;

    @GetMapping("/login")
    public String login(){

        return "login";
    }
    @ResponseBody
    @GetMapping("/login/success")
    public String loginSuccess(@AuthenticationPrincipal UserDetails userDetails){
        return "success";
    }
    @ResponseBody
    @GetMapping("/login/fail")
    public String loginFail(){

        return "fail";
    }
}
