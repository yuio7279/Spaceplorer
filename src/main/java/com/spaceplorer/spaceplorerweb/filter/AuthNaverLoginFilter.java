package com.spaceplorer.spaceplorerweb.filter;

import com.spaceplorer.spaceplorerweb.auth.Auth;
import com.spaceplorer.spaceplorerweb.auth.NaverAuth;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;



//네이버 Authorization server 접근
@RequiredArgsConstructor
@Slf4j
public class AuthNaverLoginFilter extends GenericFilterBean {

    private final String REQUEST_NAVER_URI = "/api/auth/naver/login";
    private final String AUTHORIZATION_NAVER_URL = "https://authorization-server.com/oauth/authorize";
    private final String CLIENT_ID = "your-client-id";
    private final String REDIRECT_URI = "localhost:8080/login/oauth2/code/custom";
    private final String RESPONSE_TYPE = "code";
    private final String SCOPE = "read_profile";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        //Authorization url 얻기
        if(Auth.requestMatch(REQUEST_NAVER_URI,request)){
            Auth auth = new NaverAuth(CLIENT_ID, REDIRECT_URI, RESPONSE_TYPE, SCOPE, AUTHORIZATION_NAVER_URL);
            log.info("method=doFilter, auth={}",auth);

            String authorizationRequestUri = auth.getAuthorizationRequestUrl();

            return;
            /*response.sendRedirect(authorizationRequestUri);*/
        }

        chain.doFilter(request, response);
    }
}


