package com.example.simpleoauth.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.simpleoauth.domain.user.dto.MyProfileResponse;
import com.example.simpleoauth.domain.user.service.UserService;
import com.example.simpleoauth.global.security.AuthUser;
import com.example.simpleoauth.global.security.LoginUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	@GetMapping("/me")
	public ResponseEntity<MyProfileResponse> getMyProfile(
		@LoginUser AuthUser authUser
	) {
		MyProfileResponse res = userService.getMyProfile(authUser.userId());
		return ResponseEntity.ok(res);
	}
}
