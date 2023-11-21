package com.api.blog.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.blog.config.AppConstants;
import com.api.blog.entities.User;
import com.api.blog.exceptions.ApiException;
import com.api.blog.payloads.JwtAuthRequest;
import com.api.blog.payloads.UserDto;
import com.api.blog.security.CustomUserDetailService;
import com.api.blog.security.JwtAuthResponse;
import com.api.blog.security.JwtTokenHelper;
import com.api.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/" + AppConstants.API + "/" + AppConstants.AUTH + "/")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private CustomUserDetailService customUserDetailService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;
	@Autowired
	ModelMapper modelMapper;
	
	@PostMapping("/" + AppConstants.LOGIN)
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception{
		
		this.authenticate(request.getUsername(), request.getPassword());
		
		UserDetails userDetails = this.customUserDetailService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
	
		JwtAuthResponse response = new JwtAuthResponse();
		
		response.setToken(token); 
		response.setUserDto(this.modelMapper.map((User)userDetails, UserDto.class));
		
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}
	
	private void authenticate(String username, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {

			this.authenticationManager.authenticate(authenticationToken);
			
		} catch (BadCredentialsException e) {
			
			System.out.println("Invalid username or password.");
			throw new ApiException("Invalid username or password.");
		}
		
	}
	
	@PostMapping("/" + AppConstants.REGISTER)
	private ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto){
		
		UserDto registerUser = this.userService.registerUser(userDto);
		return new ResponseEntity<UserDto>(registerUser, HttpStatus.OK);
	}

}
