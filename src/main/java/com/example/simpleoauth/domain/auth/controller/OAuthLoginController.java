package com.example.simpleoauth.domain.auth.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.simpleoauth.domain.auth.dto.OAuthLoginResponse;
import com.example.simpleoauth.domain.auth.service.OAuthLoginService;
import com.example.simpleoauth.domain.auth.constant.OAuthProvider;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth")
public class OAuthLoginController {

	private final OAuthLoginService oAuthLoginService;

	@GetMapping("/authorize/{provider}")
	public void redirectToProvider(
		@PathVariable("provider") OAuthProvider provider,
		HttpServletResponse response
	) throws IOException {
		String redirectUri = oAuthLoginService.getRedirectUri(provider);
		response.sendRedirect(redirectUri);
	}

	@GetMapping("/login/{provider}")
	public ResponseEntity<OAuthLoginResponse> login(
		@PathVariable("provider") OAuthProvider provider,
		@RequestParam("code") String code
	) {
		OAuthLoginResponse res = oAuthLoginService.login(provider, code);
		return ResponseEntity.ok(res);
	}
}
