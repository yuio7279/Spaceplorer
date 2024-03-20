package com.spaceplorer.spaceplorerweb.auth.jwt;


import com.spaceplorer.spaceplorerweb.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.spaceplorer.spaceplorerweb.common.Messages.EXPIRED_TOKEN;

//토큰을 검증하는 필터
@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {


    private final TokenProvider tokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ExpiredJwtException {
        try {

            String token_ = tokenProvider.getTokenFromRequest(request);

            if (token_ == null) {
                filterChain.doFilter(request, response);
            } else {
                String token = tokenProvider.substringToken(token_);
                if (tokenProvider.validateToken(token)) {
                    log.debug("[Found jwt token:{}]", token.substring(0,6)+"...");

                    //claims객체에서 사용자 정보 추출하기
                    Claims claims = tokenProvider.getUserInfoFromToken(token);
                    String username = claims.getSubject();
                    String role = (String) claims.get("auth");


                    //usernamePasswordAuthenticationToken, Authentication 객체 만들어서 홀더에 넣기
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, "", getRoleList(role));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.debug("[Authentication success]");

                    filterChain.doFilter(request, response);
                }
            }
            //Jwt 토큰이 만료된 경우,
        }catch (ExpiredJwtException e){
                String userName = e.getClaims().getSubject();
                log.debug(EXPIRED_TOKEN+" {}...", userName );
                log.debug("[Looking for refresh token..]");

                Role role = Role.valueOf((String)e.getClaims().get("auth"));

                //리프레시 토큰 만료 체크 & 토큰이 존재하는지도 확인
                boolean notExpired = refreshTokenProvider.validateRedisRefreshToken(userName);

                //만료됨
                if(!notExpired){
                    log.debug("[Must be re-login]");
                    filterChain.doFilter(request, response);
                    return;
                }

                //리프레시 토큰에 중복 요청 된건지 확인, jti가 존재하면 이는 중복 요청이다. 거부한다.
                boolean isJtiPresent = refreshTokenProvider.isPresentJtiAtRedis(userName);
                if(isJtiPresent){
                    log.debug("[Already issued access token key:{}]",userName);
                    log.info("[Can not issue access token key:{}]",userName);

                    refreshTokenProvider.removeRefreshTokenJti(userName);
                    refreshTokenProvider.removeRefreshToken(userName);

                    filterChain.doFilter(request, response);
                    return;
                }


                try {
                    log.debug("[Re issue in progress...]");
                    this.reIssueTokenProcess(response, userName, role);

                    filterChain.doFilter(request, response);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }



        }
    }

    private void reIssueTokenProcess(HttpServletResponse response, String userName, Role role) throws Exception {
        String createdToken = tokenProvider.createToken(userName, role);

        //리프레시 토큰또한 재발급 한다.
        String refreshToken = refreshTokenProvider.createRefreshToken(userName);
        refreshTokenProvider.saveRefreshToken(userName, refreshToken);

        //refresh token이 중복 사용됬는지 확인용 jti 저장
        refreshTokenProvider.saveRefreshTokenJti(userName, refreshToken);
        log.info("[Complete to issue new access token]");
        tokenProvider.addJwtToCookie(createdToken, response);
    }
    private static List<SimpleGrantedAuthority> getRoleList(String role) {
        List<SimpleGrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority(role));
        return grantedAuthorityList;
    }
}
