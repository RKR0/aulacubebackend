package com.aulacube.rbac.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aulacube.rbac.dto.UserDto;
import com.aulacube.rbac.exception.UserNotFoundException;
import com.aulacube.rbac.model.Role;
import com.aulacube.rbac.model.User;
import com.aulacube.rbac.repository.UserRepo;
import com.aulacube.rbac.service.UserService;
import com.aulacube.rbac.transformer.UserTransformer;




@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepo userRepo;
	
	
	
   

    // New User register
	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		// Transfer userDto to user
		User user = UserTransformer.UserDtoToUser(userDto);
		
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		// save user to database
		User ans = userRepo.save(user);
		
		// Transfer userDto to user
		UserDto updateuserDto = UserTransformer.UserToUserDto(ans);
		
		
		return updateuserDto;
}

	

	// send OTP to email verification
	@Override
	public int emailVerification(String email) {
		
		
		// Generate random code using random library 
		Random random = new Random();
	    int otp = 100000 + random.nextInt(900000);
		
	    // Mail Content
		String text = " Hi !!"

		+"\n\n Thanks for getting started with our Aula Cube! "

		+"\n\n To complete your registration, please enter the OTP code below to verify your email address "

		+"\n\n OTP Code: "+ otp

		+"\n\n Thank you for joining us! If you have any questions or need assistance, feel free to reach out. "

		+"\n\n\nBest regards,\nAulaCube";
		
		// Mail creation
		 SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
	        simpleMailMessage.setFrom("kanna51198@gmail.com");
	        simpleMailMessage.setTo(email);
	        simpleMailMessage.setSubject("Email Verification");
	        simpleMailMessage.setText(text);

	        javaMailSender.send(simpleMailMessage);
		
	     return otp;
		
	}

	// get user data by userId
	@Override
	public UserDto getuserById(Integer userId) {
		
		
		Optional<User> user= userRepo.findById(userId);
		
		if(user.isEmpty())
			throw new UserNotFoundException("Invalid User Id!!");
		
		return  UserTransformer.UserToUserDto(user.get());
	}

	// get all Users
	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		
		List<User> user= userRepo.findAll();
		
		List<UserDto> userDto = new ArrayList<>();
		for(User u:user) {
			userDto.add(UserTransformer.UserToUserDto(u));
		}
		
		return userDto;
	}
	
	// delete User by UserId
	@Override
	public boolean deleteUser(String username) {
		
		Optional<User> user= userRepo.findByEmail(username);
		
		if(user.isEmpty())
			return false;
		
		userRepo.deleteById(user.get().getId());
		
		return true;
	}

	// Update user data
	@Override
	public UserDto updateUser(UserDto userDto) {
		// TODO Auto-generated method stub
		
		Optional<User> user= userRepo.findById(userDto.getUserId());
		
		if(user.isEmpty())
			throw new UserNotFoundException("Invalid User Id!!");
		
		User updateuser = user.get();
		
		if(userDto.getAbout()!=null)
			updateuser.setAbout(userDto.getAbout());
		if(userDto.getFirstName()!=null)
			updateuser.setFirstName(userDto.getFirstName());
		if(userDto.getLastName()!=null)
			updateuser.setLastName(userDto.getLastName());
		if(userDto.getPhone()!=null)
			updateuser.setPhone(userDto.getPhone());
		if(userDto.getPassword()!=null)
			updateuser.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		User ans = userRepo.save(updateuser);
		
		UserDto updateuserDto = UserTransformer.UserToUserDto(ans);
		
		return updateuserDto;
	}



	@Override
	public boolean usertoAdmin(String username) {
		// TODO Auto-generated method stub
		
		Optional<User> user= userRepo.findByEmail(username);
		
		if(user.isEmpty())
			throw new UserNotFoundException("Invalid User Id!!");
		
		User updateuser = user.get();
		updateuser.setRole(Role.ROLE_ADMIN);
		
		User ans = userRepo.save(updateuser);
		
		return true;
	}

	
}
