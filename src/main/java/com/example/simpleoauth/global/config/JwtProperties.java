package com.example.simpleoauth.global.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties("jwt")
@Component
public class JwtProperties {
	private String secret;
	private Duration accessTokenExpiration;

	public Long getAccessTokenExpirationMillis() {
		return accessTokenExpiration.toMillis();
	}
}
