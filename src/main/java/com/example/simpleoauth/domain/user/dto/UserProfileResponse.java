package com.example.simpleoauth.domain.user.dto;

import com.example.simpleoauth.domain.user.entity.User;

public record UserProfileResponse(
	Long userId,
	String nickname
) {

	public static UserProfileResponse from(User user) {
		return new UserProfileResponse(user.getId(), user.getNickname());
	}
}
