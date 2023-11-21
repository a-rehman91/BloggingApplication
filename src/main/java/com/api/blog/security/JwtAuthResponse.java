package com.api.blog.security;

import com.api.blog.payloads.UserDto;

import lombok.Data;

@Data
public class JwtAuthResponse {

	private String token;
	private UserDto userDto;
}
