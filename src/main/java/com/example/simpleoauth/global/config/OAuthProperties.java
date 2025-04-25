package com.example.simpleoauth.global.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.example.simpleoauth.domain.auth.constant.OAuthProvider;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "oauth")
@Component
public class OAuthProperties {
	private Map<OAuthProvider, OAuthProviderProperties> providers;
}
