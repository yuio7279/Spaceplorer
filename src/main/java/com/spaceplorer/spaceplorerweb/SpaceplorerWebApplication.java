package com.spaceplorer.spaceplorerweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpaceplorerWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpaceplorerWebApplication.class, args);
	}

}
