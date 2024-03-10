package com.aulacube.rbac.transformer;

import com.aulacube.rbac.dto.UserDto;
import com.aulacube.rbac.model.Role;
import com.aulacube.rbac.model.User;

public class UserTransformer {

	 //Convert UserDto Class to User Class
    public static User UserDtoToUser(UserDto userDto){

        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .about(userDto.getAbout())
                .phone(userDto.getPhone())
                .password(userDto.getPassword())
                .role(Role.ROLE_USER)
                .build();
    }
    
    // Convert User Class to UserDto Class
    public static UserDto UserToUserDto(User userDetails){

        return UserDto.builder()
        		.userId(userDetails.getId())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .email(userDetails.getEmail())
                .about(userDetails.getAbout())
                .phone(userDetails.getPhone())
                .password(userDetails.getPassword())
                .role(userDetails.getRole().name())
                .build();
    }
	
}
