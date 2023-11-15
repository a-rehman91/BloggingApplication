package com.api.blog.services;

import java.util.List;

import com.api.blog.payloads.UserDto;

public interface UserService {

	UserDto registerUser(UserDto userDto);
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, int userId);
	UserDto getUserById(int userId);
	List<UserDto> getAllUsers();
	void deleteUser(int userId);
}
