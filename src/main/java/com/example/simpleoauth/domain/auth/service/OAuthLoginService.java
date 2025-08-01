package com.example.simpleoauth.domain.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.example.simpleoauth.global.security.JwtProvider;
import com.example.simpleoauth.domain.auth.constant.OAuthProvider;
import com.example.simpleoauth.client.OAuthUserInfo;
import com.example.simpleoauth.client.OAuthUserService;
import com.example.simpleoauth.global.config.OAuthProperties;
import com.example.simpleoauth.global.config.OAuthProviderProperties;
import com.example.simpleoauth.domain.auth.dto.OAuthLoginResponse;
import com.example.simpleoauth.domain.user.entity.User;
import com.example.simpleoauth.domain.user.entity.UserOAuthLink;
import com.example.simpleoauth.domain.user.repository.UserOAuthLinkRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuthLoginService {

	private final OAuthProperties oauthProperties;
	private final UserOAuthLinkRepository userOAuthLinkRepository;
	private final OAuthUserService oAuthUserService;
	private final JwtProvider jwtProvider;
	private final OAuthRegisterService oAuthRegisterService;

	public String getRedirectUri(OAuthProvider provider) {
		OAuthProviderProperties prop = oauthProperties.getProviders().get(provider);
		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(prop.getAuthorizeUri());
		return factory.builder()
			.queryParam("response_type", "code")
			.queryParam("client_id", prop.getClientId())
			.queryParam("redirect_uri", prop.getRedirectUri())
			.build()
			.toString();
	}

	public OAuthLoginResponse login(OAuthProvider provider, String code) {
		OAuthUserInfo userInfo = oAuthUserService.loadUser(provider, code);

		UserOAuthLink oAuthLink = userOAuthLinkRepository.findByProviderAndProviderId(provider, userInfo.providerId())
			.orElseGet(() -> oAuthRegisterService.registerOAuthUser(userInfo));

		User user = oAuthLink.getUser();
		String accessToken = jwtProvider.generateAccessToken(user.getId(), user.getEmail());
		return new OAuthLoginResponse(accessToken);
	}
}
