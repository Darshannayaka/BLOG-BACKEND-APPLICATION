package com.codewithdurgesh.blog2.exceptions;

@SuppressWarnings("serial")
public class JwtExpiredException extends RuntimeException{
	public JwtExpiredException(String message) {
        super(message);
    }
}
