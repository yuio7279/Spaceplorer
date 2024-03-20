package com.spaceplorer.spaceplorerweb.auth.social;

import com.spaceplorer.spaceplorerweb.auth.jwt.RefreshTokenProvider;
import com.spaceplorer.spaceplorerweb.auth.jwt.TokenProvider;
import com.spaceplorer.spaceplorerweb.domain.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;


//로그인 성공 시, 토큰 생성, 쿠키에 추가
@RequiredArgsConstructor
@Component
@Slf4j
public class UrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        try{
            String userName = authentication.getName();
            Role role = getRole(authentication);

            issueTokenProcess(response, userName, role);
            log.info("[Complete to login:{}, {}]",userName, role);
            response.sendRedirect("/login/success");
        } catch (Exception e){
            log.error("[Exception occurred during issuance:{}]",e.getMessage());
        }
    }

    //토큰 발급을 하나로 묶었다.
    private void issueTokenProcess(HttpServletResponse response, String userName, Role role) throws Exception {
            String createdToken = tokenProvider.createToken(userName, role);
            String refreshToken = refreshTokenProvider.createRefreshToken(userName);
            refreshTokenProvider.saveRefreshToken(userName, refreshToken);
            tokenProvider.addJwtToCookie(createdToken, response);

            //로그인 시 jti를 제거해 리프레시 토큰에 요청을 받을 수 있게 한다.
            refreshTokenProvider.removeRefreshTokenJti(userName);
            log.info("[Login success:{}]", userName);
    }
    private Role getRole(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
        return Role.valueOf(role);
    }
}
