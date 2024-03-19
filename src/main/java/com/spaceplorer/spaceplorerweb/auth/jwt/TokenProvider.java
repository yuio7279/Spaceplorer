package com.spaceplorer.spaceplorerweb.auth.jwt;

import com.spaceplorer.spaceplorerweb.domain.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static com.spaceplorer.spaceplorerweb.common.Messages.*;

@Slf4j
@Component
public class TokenProvider {
    //JWT 토큰 제공자
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER  = "Authorization";
    public final long TOKEN_TIME = 60 * 30 * 1000L; // 10분
    public static final String AUTHORIZATION_KEY = "auth"; // 사용자 권한 값의 KEY
    public static final int SUBSTRING_NUMBER = 7;

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;

    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;


        @PostConstruct
        private void init() {
            byte[] bytes = Base64.getDecoder().decode(secretKey);
            key = Keys.hmacShaKeyFor(bytes);
        }


        // header 토큰을 가져오기 Keys.hmacShaKeyFor(bytes);
        public String resolveToken(HttpServletRequest request) {
            String bearerToken= request.getHeader(AUTHORIZATION_HEADER);
            if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)){
                return bearerToken.substring(SUBSTRING_NUMBER);
            }
            return null;
        }

        // 토큰 생성
        public String createToken(String username, Role role) {

            Date date = new Date();

            String createdToken = BEARER_PREFIX +
                    Jwts.builder()
                            .setSubject(username) // 사용자 식별자값(ID)
                            .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                            .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                            .setIssuedAt(date) // 발급일
                            .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                            .compact();
            log.debug("[Created Token:{}]", createdToken.substring(0,13)+"...");
            return createdToken;
        }

        // JWT Cookie 에 저장
        public void addJwtToCookie(String token, HttpServletResponse res) {
            try {
                token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

                Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token); // Name-Value
                cookie.setPath("/");

                // Response 객체에 Cookie 추가
                res.addCookie(cookie);
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }

        // JWT 토큰 substring
        public String substringToken(String tokenValue) {
            if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
                return tokenValue.substring(SUBSTRING_NUMBER);
            }
            log.error(NOT_FOUND_TOKEN);
            throw new NullPointerException(NOT_FOUND_TOKEN);
        }

        // 토큰 검증
        public boolean validateToken(String token) {
            try {
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
                return true;
            } catch (SecurityException | MalformedJwtException | SignatureException e) {
                log.error(INVALID_TOKEN_SIGNATURE);
            } catch (ExpiredJwtException e) {
                log.warn(EXPIRED_TOKEN+" {}...]",token.substring(0, SUBSTRING_NUMBER) );
                log.debug("[Looking for refresh token..]");

            } catch (UnsupportedJwtException e) {
                log.error(UNSUPPORTED_TOKEN);
            } catch (IllegalArgumentException e) {
                log.error(INVALID_TOKEN);
            }
            return false;
        }



        // 토큰에서 사용자 정보 가져오기
        public Claims getUserInfoFromToken(String token) throws ExpiredJwtException {
            try {
                Claims body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
                log.debug("[Found Claims:{}]",body.toString());
                return body;

            } catch (ExpiredJwtException e) {
                log.error(EXPIRED_TOKEN);
                return e.getClaims();
            }
        }
        // HttpServletRequest 에서 Cookie Value : JWT 가져오기
        public String getTokenFromRequest(HttpServletRequest req) {
            Cookie[] cookies = req.getCookies();

            if (cookies == null) {
                log.debug("[CookieList is Empty]");
                return null;
            }

                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
                        log.debug("[Found cookie : {}]",cookie.getName());
                        try {
                            String decode = URLDecoder.decode(cookie.getValue(), "UTF-8");
                            log.debug("[Decode Complete. ]");
                            return decode;
                        } catch (UnsupportedEncodingException e) {
                            log.error("[Un supported encoding method.]");
                            return null;
                        }
                    }
                }

            return null;
        }

    }


