package com.spaceplorer.spaceplorerweb.auth.jwt;


import io.jsonwebtoken.Claims;
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

//토큰을 검증하는 필터
@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("[JwtTokenFilter:]");
        String token_ = tokenProvider.getTokenFromRequest(request);

        if(token_ == null){
            filterChain.doFilter(request, response);
        }else{
            String token = tokenProvider.substringToken(token_);
            if(tokenProvider.validateToken(token))
            {
                log.debug("[Found jwt token:{}]",token);

                //claims객체에서 사용자 정보 추출하기
                Claims claims = tokenProvider.getUserInfoFromToken(token);
                String username = claims.getSubject();
                String role = (String) claims.get("auth");


                //usernamePasswordAuthenticationToken, Authentication 객체 만들어서 홀더에 넣기
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, "", getRoleList(role));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("[Authentication success:]");

                filterChain.doFilter(request, response);
            }
        }
    }

    private static List<SimpleGrantedAuthority> getRoleList(String role) {
        List<SimpleGrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority(role));
        return grantedAuthorityList;
    }
}
