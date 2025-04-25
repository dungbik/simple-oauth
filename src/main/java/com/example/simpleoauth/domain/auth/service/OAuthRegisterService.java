package com.example.simpleoauth.domain.auth.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.simpleoauth.client.OAuthUserInfo;
import com.example.simpleoauth.domain.auth.entity.User;
import com.example.simpleoauth.domain.auth.entity.UserOAuthLink;
import com.example.simpleoauth.domain.auth.repository.UserOAuthLinkRepository;
import com.example.simpleoauth.domain.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuthRegisterService {

	private final UserRepository userRepository;
	private final UserOAuthLinkRepository userOAuthLinkRepository;

	@Retryable(
		retryFor = { DataIntegrityViolationException.class },
		maxAttempts = 2,
		backoff = @Backoff(delay = 100)
	)
	@Transactional
	public UserOAuthLink registerOAuthUser(OAuthUserInfo userInfo) {
		User user = userRepository.save(User.builder()
			.email(userInfo.email())
			.nickname(userInfo.nickname())
			.build());

		return userOAuthLinkRepository.save(UserOAuthLink.builder()
			.provider(userInfo.provider())
			.providerId(userInfo.providerId())
			.user(user)
			.build());
	}
}
