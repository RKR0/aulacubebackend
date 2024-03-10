package com.aulacube.rbac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aulacube.rbac.dto.UserDto;
import com.aulacube.rbac.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/user")
public class UserController {

    final UserService userService;

    @Autowired
    public UserController(UserService userService){
      this.userService = userService;
  }
	
	// Update User Profile
    @PreAuthorize("hasRole('ROLE_ADMIN') or #username == authentication.principal.username")
	@PutMapping("/update/username/{username}")
	public ResponseEntity updateUser(@PathVariable String username,@RequestBody UserDto userDto){
		
		try {
			UserDto updateuserdto = userService.updateUser(userDto);
			return new ResponseEntity(updateuserdto,HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
}
	
	
	// get User by userId
	@GetMapping("/userid/{userid}")
	public ResponseEntity getuserById(@PathVariable Integer userid){
			
			try {
				UserDto user = userService.getuserById(userid);
				return new ResponseEntity(user,HttpStatus.OK);
			}
			catch(Exception e){
				return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
			}
}
		
	// get all Users
	@GetMapping("/users")
	public ResponseEntity getAllUsers(){
					
		try {
			 List<UserDto> user = userService.getAllUsers();
		 	return new ResponseEntity(user,HttpStatus.OK);
	    }
		catch(Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
}
					
	// Delete User by userId
	@PreAuthorize("hasRole('ROLE_ADMIN') or #username == authentication.principal.username")
	@DeleteMapping("delete/username/{username}")
	public ResponseEntity deleteUser(@PathVariable String username){
					
		try {
			Boolean user = userService.deleteUser(username);
			return new ResponseEntity("User Sucessfully Deleted!!",HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("usertoadmin/username/{username}")
	public ResponseEntity usertoAdmin(@PathVariable String username){
					
		try {
			Boolean user = userService.usertoAdmin(username);
			return new ResponseEntity("User Sucessfully Updated!!",HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
}
					
}
