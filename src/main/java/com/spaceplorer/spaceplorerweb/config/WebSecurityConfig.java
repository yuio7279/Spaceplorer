package com.spaceplorer.spaceplorerweb.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {

    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     * WebSecurityConfigrerAdapter는 더이상 사용되지 않는다 5.7~
     * SecurityFilterChain bean 방식을 사용한다.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("시큐리티 필터 체인 시작");
        //csrf disable
        //세션방식에서는 세션이 항상 고정되기 때문에 csrf공격을 반드시 방어해주어야 한다.
        //jwt 방식은 세션을 stateless 상태로 관리하기 때문에 csrf에 대한 공격을 방어 할 필요는 없다.
        http
                .csrf(AbstractHttpConfigurer::disable);

        //Form 로그인 방식 disable
        //jwt방식으로 로그인 하기 때문에 form로그인을 사용하지 않는다.
        http
                .formLogin(AbstractHttpConfigurer::disable);

        //http basic 인증 방식 disable
        http
                .httpBasic(AbstractHttpConfigurer::disable);

        http
                .headers(headers -> headers
                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        //경로별 인가
        http

                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/login")
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(oAuth2UserService))
                        .successHandler(authenticationSuccessHandler)
                        .failureUrl("/login/fail")
                )
                .authorizeHttpRequests((auth) -> auth
                        //h2
                        .requestMatchers("/h2-console/**").permitAll()
                        //static
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()

                        //form
                        .requestMatchers("/","/index.html","/permitAllContents.html").permitAll()
                        .requestMatchers("/login/**","/logout/**","/error").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")

                        //api
                        .requestMatchers("/api/**").permitAll()

                        .anyRequest().permitAll()
                )
                .logout(logout -> logout
                                        .logoutUrl("/logout/success") // 로그아웃 처리 URL
                                        .logoutSuccessUrl("/") // 로그아웃 성공 후 리다이렉트할 URL
                                        .invalidateHttpSession(true) // 세션 무효화
                                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                                        .clearAuthentication(true) // 인증 정보 클리어
                );

        //세션 설정 항상 stateless하게 관리
        http
                .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //필터체인 연결
/*        http
                // 기타 필요한 http 보안 구성
                .addFilterBefore(authKakaoLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authNaverLoginFilter, UsernamePasswordAuthenticationFilter.class);*/

        return http.build();
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true); // 세미콜론 허용
        return firewall;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    }

}

