package com.example.simpleoauth.domain.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.simpleoauth.domain.user.dto.MyProfileResponse;
import com.example.simpleoauth.domain.user.dto.UserProfileResponse;
import com.example.simpleoauth.domain.user.entity.User;
import com.example.simpleoauth.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public MyProfileResponse getMyProfile(Long userId) {
		User user = findUserByIdOrThrow(userId);
		return MyProfileResponse.from(user);
	}

	public UserProfileResponse getUserProfile(Long userId) {
		User user = findUserByIdOrThrow(userId);
		return UserProfileResponse.from(user);
	}

	private User findUserByIdOrThrow(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."));
	}
}
