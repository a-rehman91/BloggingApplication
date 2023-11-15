package com.api.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.api.blog.config.AppConstants;
import com.api.blog.entities.User;
import com.api.blog.exceptions.ResourceNotFoundException;
import com.api.blog.repositories.UserRepo;

@Component
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		//loading user from database.
		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException(AppConstants.USER, AppConstants.USER_ID, username));
		
		return user;
	}

}
