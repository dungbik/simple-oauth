package com.example.simpleoauth.client;

import com.example.simpleoauth.domain.auth.constant.OAuthProvider;

public record OAuthUserInfo(
	OAuthProvider provider,
	String providerId,
	String email,
	String nickname
) {

}
