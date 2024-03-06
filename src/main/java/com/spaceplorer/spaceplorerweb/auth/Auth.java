package com.spaceplorer.spaceplorerweb.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


public interface Auth {

    //예를들어, 네이버필터의 requestUri는 /api/auth/naver/login
    // request가 requestUri와 일치하는지 판별
    static boolean requestMatch(String requestUri, HttpServletRequest request){
        return new AntPathRequestMatcher(requestUri).matches(request);
    }

    String getAuthorizationRequestUrl();

}
