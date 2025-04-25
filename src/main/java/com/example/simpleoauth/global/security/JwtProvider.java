package com.example.simpleoauth.global.security;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.example.simpleoauth.global.config.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtProvider {

	private final JwtProperties jwtProperties;

	private SecretKey secretKey;

	@PostConstruct
	public void init() {
		this.secretKey
			= Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtProperties.getSecret()));
	}

	public String generateAccessToken(Long userId, String email) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + jwtProperties.getAccessTokenExpirationMillis());

		return Jwts.builder()
			.subject(String.valueOf(userId))
			.claim("email", email)
			.issuedAt(now)
			.expiration(expiry)
			.signWith(secretKey)
			.compact();
	}

	public UserAuth parseUserFromToken(String token) {
		Claims claims = Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload();

		Long userId = Long.parseLong(claims.getSubject());
		String email = claims.get("email", String.class);
		return new UserAuth(userId, email);
	}
}
