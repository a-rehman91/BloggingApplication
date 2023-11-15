package com.api.blog.services.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.This;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.blog.config.AppConstants;
import com.api.blog.entities.Role;
import com.api.blog.entities.User;
import com.api.blog.exceptions.ResourceNotFoundException;
import com.api.blog.payloads.UserDto;
import com.api.blog.repositories.RoleRepo;
import com.api.blog.repositories.UserRepo;
import com.api.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {

		User user = dtoToUser(userDto);
		
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		return userToDto(this.userRepo.save(user));
	}

	@Override
	public UserDto updateUser(UserDto userDto, int userId) {

		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		
		return userToDto(this.userRepo.save(user));
	}

	@Override
	public UserDto getUserById(int userId) {

		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));

		UserDto userDto = modelMapper.map(user, UserDto.class);

		
		return userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users = this.userRepo.findAll();

		return users.stream().map(user->userToDto(user)).collect(Collectors.toList());
	}

	@Override
	public void deleteUser(int userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));
		this.userRepo.delete(user);
	}
	
	@Override
	public UserDto registerUser(UserDto userDto) {

		User user = dtoToUser(userDto);
		Role role = this.roleRepo.findById(AppConstants.NORMAL_ROLE).get();
		
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		Set<Role> r = new HashSet<>();
		r.add(role);
		
		user.setRoles(r);
		user.getRoles().add(role);
		
		return userToDto(this.userRepo.save(user));
	}
	
	public User dtoToUser(UserDto userDto) {
				
		return modelMapper.map(userDto, User.class);
	}

	public UserDto userToDto(User user) {

		return modelMapper.map(user, UserDto.class);
	}


}
