package com.example.simpleoauth.global.security;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(AuthUser.class) &&
			parameter.hasParameterAnnotation(LoginUser.class);
	}

	@Override
	public AuthUser resolveArgument(
		MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory
	) {
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		AuthUser user = (AuthUser) request.getAttribute(AuthConstants.AUTH_USER);
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "해당 요청은 로그인 후 이용할 수 있습니다.");
		}

		return user;
	}
}
