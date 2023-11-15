package com.api.blog.controllers;

import org.aspectj.util.GenericSignature.SimpleClassTypeSignature;
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
import com.api.blog.exceptions.ApiException;
import com.api.blog.payloads.JwtAuthRequest;
import com.api.blog.payloads.UserDto;
import com.api.blog.security.CustomUserDetailService;
import com.api.blog.security.JwtAuthResponse;
import com.api.blog.security.JwtTokenHelper;
import com.api.blog.services.UserService;

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
	
	@PostMapping("/" + AppConstants.LOGIN)
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception{
		
		this.authenticate(request.getUsername(), request.getPassword());
		
		UserDetails userDetails = this.customUserDetailService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse response = new JwtAuthResponse();
		
		response.setToken(token); 
		
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
	private ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		
		UserDto registerUser = this.userService.registerUser(userDto);
		return new ResponseEntity<UserDto>(registerUser, HttpStatus.OK);
	}

}
