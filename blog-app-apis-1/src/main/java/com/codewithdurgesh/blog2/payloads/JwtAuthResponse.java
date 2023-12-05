package com.codewithdurgesh.blog2.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class JwtAuthResponse {

	private String token;
	
	private UserDto user;
}
