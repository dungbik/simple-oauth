package com.example.simpleoauth.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.simpleoauth.domain.auth.constant.OAuthProvider;
import com.example.simpleoauth.domain.user.entity.UserOAuthLink;

public interface UserOAuthLinkRepository extends JpaRepository<UserOAuthLink, Long> {

	@EntityGraph(attributePaths = "user")
	Optional<UserOAuthLink> findByProviderAndProviderId(OAuthProvider provider, String providerId);
}
