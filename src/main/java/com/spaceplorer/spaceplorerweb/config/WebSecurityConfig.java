package com.spaceplorer.spaceplorerweb.config;

import com.spaceplorer.spaceplorerweb.filter.AuthKakaoLoginFilter;
import com.spaceplorer.spaceplorerweb.filter.AuthNaverLoginFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {

    private final AuthKakaoLoginFilter authKakaoLoginFilter;
    private final AuthNaverLoginFilter authNaverLoginFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {
        log.info("method=filterChain");
        //csrf disable
        //세션방식에서는 세션이 항상 고정되기 때문에 csrf공격을 반드시 방어해주어야 한다.
        //jwt 방식은 세션을 stateless 상태로 관리하기 때문에 csrf에 대한 공격을 방어 할 필요는 없다.
        http
                .csrf((auth) -> auth.disable());

        //Form 로그인 방식 disable
        //jwt방식으로 로그인 하기 때문에 form로그인을 사용하지 않는다.
        http
                .formLogin((auth) -> auth.disable());

        //http basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());

        //경로별 인가
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/api/auth/**").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

        //세션 설정 항상 stateless하게 관리
        http
                .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //필터체인 연결
        http
                // 기타 필요한 http 보안 구성
                .addFilterBefore(authKakaoLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authNaverLoginFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}