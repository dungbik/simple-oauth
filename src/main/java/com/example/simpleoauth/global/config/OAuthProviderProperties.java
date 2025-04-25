package com.example.simpleoauth.global.config;

import java.util.Map;

import com.example.simpleoauth.domain.auth.constant.OAuthProvider;

import lombok.Data;

@Data
public class OAuthProviderProperties {
	private OAuthProvider provider;
	private String clientId;
	private String authorizeUri;
	private String tokenUri;
	private String userInfoUri;
	private String redirectUri;
	private Map<String, String> fields;
}