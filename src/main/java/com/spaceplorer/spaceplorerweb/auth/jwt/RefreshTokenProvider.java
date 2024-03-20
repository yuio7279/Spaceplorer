package com.spaceplorer.spaceplorerweb.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.spaceplorer.spaceplorerweb.common.Messages.*;
import static com.spaceplorer.spaceplorerweb.common.Messages.INVALID_TOKEN;

@RequiredArgsConstructor
@Component
@Slf4j
public class RefreshTokenProvider {

    private final RedisTemplate<String, Object> redisTemplate;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private static final Long REFRESH_TOKEN_EXPIRE_TIME = 14L; //14일

    //발행자
    private static final String ISSUER = "spaceplorer";

    @Value("${jwt.refresh.secret.key}")
    private String SECRET_KEY;

    //secret key 디코딩해서 key에 담는다.
    @PostConstruct
    private void init() {
        byte[] bytes = Base64.getDecoder().decode(SECRET_KEY);
        this.key = Keys.hmacShaKeyFor(bytes);
    }

    /**
     * 리프레시 토큰 생성
     * @param username authentication.getName()
     * @return refresh token
     */
    public String createRefreshToken(String username){;
        if(!validKey(username)){
            return null;
        }
        log.debug("[CreateRefreshToken() username:{}]",username);
        Date now = new Date();
        Date expireDate = new Date
                (now.getTime() + REFRESH_TOKEN_EXPIRE_TIME * 1000 * 60 * 60 * 24); //14일

        String token = Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(username) // 사용자 식별자값(ID)
                .setId(UUID.randomUUID().toString()) //토큰 중복 사용 검사를 위한 jti
                .setExpiration(expireDate) // 만료 시간
                .setIssuedAt(now) // 발급일
                .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                .compact();

        log.debug("[Created refresh Token:{}...]",token.substring(0, 10));
        return token;

    }

    /**
     * Redis에 리프레시 토큰 저장
     * @param username authentication.getName()
     * @param refreshToken this.createRefreshToken():String
     */
    @Transactional
    public void saveRefreshToken(String username, String refreshToken){
        //데이터의 검증
        if(!validKey(username)){
            return;
        }
        //token이 JWT 형식인지를 판별
        if(!validateRefreshTokenFormat(refreshToken)){
            return;
        }
        //Redis 저장
        redisTemplate.opsForValue().set(
                getRefreshKeyPattern(username),
                refreshToken,
                REFRESH_TOKEN_EXPIRE_TIME,
                TimeUnit.DAYS);

        log.debug("[Complete save refresh token to redis key:{}]",username);
    }


    //리프레시 토큰 Redis에서 꺼내기
    public String getRefreshToken(String username){
        log.debug("[find refresh token.. key:{}]",username);
        if(!validKey(username)){
            return null;
        };

        Object refreshToken = redisTemplate
                .opsForValue()
                .get(Objects.requireNonNull(getRefreshKeyPattern(username)));

        if(refreshToken == null){
            log.debug("[Not found refresh token]");
            return null;
        }
        return (String) refreshToken;
    }


    //리프레시 토큰 유효성 검증하기, 만료? 유효한지? 서명확인 등
    public boolean validateRedisRefreshToken(String username){
        if(!validKey(username)){
            return false;
        }
        String refreshToken = (String) redisTemplate
                .opsForValue()
                .get(Objects.requireNonNull(getRefreshKeyPattern(username)));


        if(!validateRefreshTokenFormat(refreshToken)) {
            return false;
        }

        try {
                Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(refreshToken);
                return true;
            } catch (SecurityException | MalformedJwtException | SignatureException e) {
                log.error(INVALID_TOKEN_SIGNATURE);
            } catch (ExpiredJwtException e) {
            assert refreshToken != null;
            log.warn(EXPIRED_REFRESH_TOKEN+" {}...]", refreshToken.substring(0,7));
            } catch (UnsupportedJwtException e) {
                log.error(UNSUPPORTED_TOKEN);
            } catch (IllegalArgumentException e) {
                log.error(INVALID_TOKEN);
            }
            return false;
    }

    // 레디스에서 리프레시 토큰 제거하기
    public void removeRefreshToken(String username){
        if(!validKey(username)){
            return;
        }

        Boolean isPresent = redisTemplate.hasKey(getRefreshKeyPattern(username));
        log.debug("[Is present:{}]",isPresent);
        if(Boolean.FALSE.equals(isPresent)){
            return;
        }

        Boolean isDeleted = redisTemplate.delete(getRefreshKeyPattern(username));
        log.debug("[Is deleted:{}]",isDeleted);

        if(Boolean.FALSE.equals(isDeleted)){
            log.error("[Failed to delete refreshToken key:{}]",username);
            return;
        }
        log.debug("[Delete complete refreshToken key:{}]", username);

    }
    //jti 제거하기
    public void removeRefreshTokenJti(String username) {
        if(!validKey(username)){
            return;
        }
        Boolean isPresent = redisTemplate.hasKey(Objects.requireNonNull(getJtiKeyPattern(username)));
        if(Boolean.FALSE.equals(isPresent)){
            return;
        }

        Boolean isDeleted = redisTemplate.delete(Objects.requireNonNull(getJtiKeyPattern(username)));
        log.debug("[Is deleted:{}]",isDeleted);

        if(Boolean.FALSE.equals(isDeleted)){
            log.error("[Failed to delete refreshToken jti key:{}]",username);
            return;
        }
        log.debug("[Delete complete refreshToken jti key:{}]", username);

    }
    //jti를 Redis에 저장한다. 리프레시 토큰을 통해 엑세스토큰이 발급되었을 경우 호출한다.
    //후에 jti가 조회된다면, 해당 리프레시 토큰은 재발급을 반복 요청한것이 되므로 거부한다.
    public void saveRefreshTokenJti(String username, String refreshToken) {
        try {
            if(refreshToken == null || refreshToken.trim().isEmpty()){
                log.error("[Not found refreshToken]");
                return;
            }

            if(!validKey(username)){
                return;
            }


            String jti = getJtiByRefreshToken(refreshToken);

            redisTemplate.opsForValue().set(
                    Objects.requireNonNull(getJtiKeyPattern(username)),
                    jti,
                    REFRESH_TOKEN_EXPIRE_TIME,
                    TimeUnit.DAYS);

            log.debug("[Complete save jti to redis key:{}]",username);

        } catch (SignatureException e) {
            log.error("[Exception occurred during save jti:{}]",e.getMessage());
        }
    }

    public boolean isPresentJtiAtRedis(String username){

        String jti = (String) redisTemplate
                .opsForValue()
                .get(Objects.requireNonNull(getJtiKeyPattern(username)));

        if(jti == null || jti.trim().isEmpty()){
            log.debug("[Not present jti]");
            return false;
        }

        log.debug("[Present jti:{}]", jti);
        return true;
    }


    /*-----private-----*/

    //토큰 고유 id를 가져오기, 중복 확인용
    private String getJtiByRefreshToken(String refreshToken) {

        if(refreshToken == null || refreshToken.trim().isEmpty()){
            log.error("[Not found refreshToken]");
            return null;
        }
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(refreshToken).getBody();

        return claims.getId();
    }

    //리프레시 토큰에 저장할 key를 반환

    /**
     *
     * @param username authentication.getName()
     * @return prefix + username
     */
    private String getRefreshKeyPattern(String username) {
        if(!validKey(username)){
            return null;
        }
        return "refresh_token:"+username;
    }
    private String getJtiKeyPattern(String username) {
        if(!validKey(username)){
            return null;
        }
        return "refresh_token_jti:"+username;
    }

    //키 검증 null인지, 공백인지
    private boolean validKey(String username) {
        if(username == null || username.trim().isEmpty()){
            log.error("[Key is null]");
            return false;
        }
        return true;
    }

    //리프레시 토큰 형식 검증하기, 토큰 생성 시 내부실행
    private boolean validateRefreshTokenFormat(String refreshToken){
        if(refreshToken == null || refreshToken.trim().isEmpty()){
            log.debug("[Not found refreshToken]");
            return false;
        }
        // 토큰이 세 부분(header, payload, signature)으로 구성되어 있는지 확인
        String[] parts = refreshToken.split("\\.");
        if(parts.length != 3){
            log.error("[Not JWT format]");
            return false;
        }
        return true;
    }

}
