package com.spaceplorer.spaceplorerweb.config;

import com.spaceplorer.spaceplorerweb.filter.AuthNaverLoginFilter;
import com.spaceplorer.spaceplorerweb.filter.AuthKakaoLoginFilter;
import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FilterConfig {
    @Bean
    public Filter authNaverLoginFilter() {
        return new AuthNaverLoginFilter();
    }

    @Bean
    public Filter authKakaoLoginFilter() {
        return new AuthKakaoLoginFilter();
    }


}
