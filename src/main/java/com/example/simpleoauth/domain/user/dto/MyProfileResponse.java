package com.example.simpleoauth.domain.user.dto;

import com.example.simpleoauth.domain.user.entity.User;

public record MyProfileResponse(
	Long userId,
	String email,
	String nickname
) {
	public static MyProfileResponse from(User user) {
		return new MyProfileResponse(user.getId(), user.getEmail(), user.getNickname());
	}
}
