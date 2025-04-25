package com.example.simpleoauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class SimpleOauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleOauthApplication.class, args);
	}

}
