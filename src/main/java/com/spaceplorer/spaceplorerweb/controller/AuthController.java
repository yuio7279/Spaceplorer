package com.spaceplorer.spaceplorerweb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class AuthController {
    @GetMapping("/login")
    public String login(){

        log.debug("move to login page ");
        return "login";
    }
    @ResponseBody
    @GetMapping("/login/success")
    public String loginSuccess(){

        log.debug("Login success ");
        return "Login success";
    }
    @ResponseBody
    @GetMapping("/login/fail")
    public String loginFail(){
        log.debug("login Fail");
        return "login Fail";
    }
    @ResponseBody
    @GetMapping("/logout/success")
    public String logoutSuccess(){

        log.debug("Logout success ");
        return "Logout success";
    }
    @ResponseBody
    @GetMapping("/logout/fail")
    public String logoutFail(){

        log.debug("Logout fail ");
        return "Logout fail";
    }
}
