package com.aulacube.rbac.security;



import com.aulacube.rbac.dto.UserDto;

import lombok.Data;

@Data
public class JwtAuthResponse {

	private String token;
	
	private UserDto user;
}
