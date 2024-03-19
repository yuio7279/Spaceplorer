package com.spaceplorer.spaceplorerweb.service;

import com.spaceplorer.spaceplorerweb.auth.jwt.RefreshTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
        private final RefreshTokenProvider refreshTokenProvider;

    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        removeRefreshToken();
        request.logout();
        removeCookie(request, response);
        log.info("[Complete to logout]");
    }

    private void removeCookie(HttpServletRequest request, HttpServletResponse response) {
        log.debug("[Remove cookie..]");
        // 요청으로부터 모든 쿠키를 가져옴
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            log.debug("[Cookies are null]");
            return;
        }

        for (Cookie cookie : cookies) {
                if ("JSESSIONID".equals(cookie.getName())
                        || "Authorization".equals(cookie.getName())) {
                    //찾아서 삭제 처리
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            log.debug("[Removed cookie {}]", cookie.getName());
            }

    }

    private void removeRefreshToken() {
        log.debug("[Remove refresh token..]");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String logoutUserName = authentication.getName();
        refreshTokenProvider.removeRefreshToken(logoutUserName);
    }
}
