package com.example.simpleoauth.domain.auth.constant;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OAuthProvider {
	KAKAO;

	@JsonCreator
	public static OAuthProvider fromConfig(String value) {
		return OAuthProvider.valueOf(value.toUpperCase());
	}

	public static OAuthProvider from(String value) {
		try {
			return fromConfig(value);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용할 수 없는 provider 입니다");
		}
	}
}
