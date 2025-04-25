package com.example.simpleoauth.global.config;

import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.simpleoauth.global.security.AuthUserArgumentResolver;
import com.example.simpleoauth.global.security.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new AuthUserArgumentResolver());
	}

	@Bean
	public FilterRegistrationBean<JwtAuthFilter> jwtAuthenticationFilter(JwtAuthFilter jwtAuthFilter) {
		FilterRegistrationBean<JwtAuthFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(jwtAuthFilter);
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}
}
