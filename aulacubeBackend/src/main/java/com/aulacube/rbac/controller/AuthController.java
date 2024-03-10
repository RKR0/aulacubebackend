package com.aulacube.rbac.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aulacube.rbac.dto.UserDto;
import com.aulacube.rbac.exception.UserNotFoundException;
import com.aulacube.rbac.model.User;
import com.aulacube.rbac.security.JwtAuthRequest;
import com.aulacube.rbac.security.JwtAuthResponse;
import com.aulacube.rbac.security.JwtTokenHelper;
import com.aulacube.rbac.service.UserService;
import com.aulacube.rbac.transformer.UserTransformer;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUser(UserTransformer.UserToUserDto((User)(userDetails)));
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	// verify email address
	@GetMapping("/verifyemail/{email}")
	public ResponseEntity emailVerification(@PathVariable String email){
			
			
			try {
				Integer num = userService.emailVerification(email);		
				return new ResponseEntity(num,HttpStatus.OK);
				
			}
			catch(Exception e){
				return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
			}
}
		
	// New User register
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerNewUser(@Valid @RequestBody UserDto userDto){
			
			try {
				UserDto createuserdto = userService.registerNewUser(userDto);
				return new ResponseEntity<UserDto>(createuserdto, HttpStatus.CREATED);
				
			}
			catch(Exception e){
				return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
			}
		}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {

			this.authenticationManager.authenticate(authenticationToken);

		} catch (BadCredentialsException e) {
			System.out.println("Invalid Detials !!");
			throw new UserNotFoundException("Invalid username or password !!");
		}

	}

	

}

