package com.aulacube.rbac.service;

import java.util.List;

import com.aulacube.rbac.dto.UserDto;





public interface UserService {
	
	
	UserDto registerNewUser(UserDto user);
	
	int emailVerification(String email);
	
	boolean deleteUser(String username);
	
	UserDto updateUser(UserDto user);
	
	UserDto getuserById(Integer userId);
	
	List<UserDto> getAllUsers();

	boolean usertoAdmin(String username);

	
	
}
