package com.spaceplorer.spaceplorerweb.auth.social;

import com.spaceplorer.spaceplorerweb.auth.jwt.RefreshTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LogoutRemoveTokenHandler implements LogoutHandler {

    private final RefreshTokenProvider refreshTokenProvider;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try{

            String userName = authentication.getName();
            refreshTokenProvider.removeRefreshToken(userName);
        }catch (Exception e){
            log.error("[Error during logout handler:{}]",e.getMessage());
        }
    }
}
