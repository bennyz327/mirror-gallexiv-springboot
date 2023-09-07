package com.team.gallexiv.common.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenException extends AuthenticationException {
	public JwtTokenException(String msg) {
		super(msg);
	}
}