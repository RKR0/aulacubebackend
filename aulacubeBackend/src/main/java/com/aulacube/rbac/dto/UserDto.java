package com.aulacube.rbac.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	int userId;
	
	@NotEmpty(message = "Email is required")
    @Email(message = "Invalid email address")
	String email;
	
	@NotEmpty(message = "Name is required")
	@Pattern(regexp="([A-Za-z]+( [A-Za-z]+)*)",message="Please enter valid Name !!")
	@Size(min = 3, message = "Name should be at least 3 characters")
	String firstName;
	
	String lastName;
	
	@NotEmpty(message = "Phone Number is required")
	@Pattern(regexp="(^[6-9]\\d{9}$)",message="Please enter valid mobile Number !!")
	String phone;
	
	@NotEmpty
	@Size(min=6,message="Password must contain minimum 6 Characters !!")
	String password;
	
	String about;
	
	
	String role;
}
