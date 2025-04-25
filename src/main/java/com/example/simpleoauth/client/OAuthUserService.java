package com.example.simpleoauth.client;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.example.simpleoauth.global.config.OAuthProperties;
import com.example.simpleoauth.domain.auth.constant.OAuthProvider;
import com.example.simpleoauth.global.config.OAuthProviderProperties;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuthUserService {

	private final OAuthProperties oauthProperties;
	private final WebClient webClient;

	public OAuthUserInfo loadUser(OAuthProvider provider, String code) {
		OAuthProviderProperties prop = oauthProperties.getProviders().get(provider);
		JsonNode tokenResponse = webClient.post()
			.uri(prop.getTokenUri())
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
			.body(BodyInserters.fromFormData("grant_type", "authorization_code")
				.with("client_id", prop.getClientId())
				.with("redirect_uri", prop.getRedirectUri())
				.with("code", code))
			.retrieve()
			.bodyToMono(JsonNode.class)
			.block();

		String accessToken = Optional.ofNullable(tokenResponse)
			.map(t -> t.get("access_token"))
			.map(JsonNode::asText)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "로그인 과정에서 오류가 발생하였습니다."));

		JsonNode userJson = webClient.get()
			.uri(prop.getUserInfoUri())
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
			.retrieve()
			.bodyToMono(JsonNode.class)
			.block();

		return extractUserInfo(userJson, prop);
	}

	private OAuthUserInfo extractUserInfo(JsonNode rawJson, OAuthProviderProperties prop) {
		String providerId = readField(rawJson, prop.getFields().get("id"));
		String email = readField(rawJson, prop.getFields().get("email"));
		String nickname = readField(rawJson, prop.getFields().get("nickname"));
		return new OAuthUserInfo(prop.getProvider(), providerId, email, nickname);
	}

	private String readField(JsonNode node, String path) {
		String[] keys = path.split("\\.");
		JsonNode current = node;
		for (String key : keys) {
			current = current.path(key);
		}
		return current.isMissingNode() ? null : current.asText();
	}
}
