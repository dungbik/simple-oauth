package com.example.simpleoauth.global.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.simpleoauth.domain.auth.constant.OAuthProvider;

@Component
public class StringToOAuthProviderConverter implements Converter<String, OAuthProvider> {

	@Override
	public OAuthProvider convert(String source) {
		return OAuthProvider.from(source.toUpperCase());
	}
}
