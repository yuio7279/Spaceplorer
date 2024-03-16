package com.spaceplorer.spaceplorerweb.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
//비동기 처리용
@EnableAsync
public class BeanConfig {


    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
