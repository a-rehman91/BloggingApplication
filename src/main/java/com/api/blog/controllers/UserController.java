package com.api.blog.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.api.blog.config.AppConstants;
import com.api.blog.payloads.ApiResponse;
import com.api.blog.payloads.UserDto;
import com.api.blog.services.FileService;
import com.api.blog.services.UserService;
import com.api.blog.services.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.FileSerializer;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/" + AppConstants.API + "/" + AppConstants.USERS)
public class UserController {
	
	@Autowired
	UserServiceImpl userService;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private FileService fileService;
	@Value("${project.image}")
	private String path;
	
	//POST - CREATE USER.
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		 
		UserDto createUser = this.userService.createUser(userDto);
		
		return new ResponseEntity<UserDto>(createUser, HttpStatus.CREATED);
	}
	
	@PostMapping("/user-info")
	public ResponseEntity<?> addUserInfo(@RequestParam("file") MultipartFile file, @RequestParam("user_data") String userData) throws IOException{

		System.out.println(file.getOriginalFilename());
		System.out.println(userData);
		
		UserDto userDto = null;
		//json string to object mapper
		try {
			userDto = objectMapper.readValue(userData, UserDto.class);
			System.out.println(userData);
			
			//update file
			String uploadedFileName = this.fileService.uploadImage(path, file);

			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
		}
		
		return ResponseEntity.ok(userDto);
	}
	
	//PUT - UPDATE USER.
	@PutMapping("/{" + AppConstants.USER_ID_PV + "}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable(AppConstants.USER_ID_PV) Integer userId){
		
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK);
	}

	//DELETE - DELETE USER.
	@PreAuthorize("hasRole('ADMIN')") 
	@DeleteMapping("/{" + AppConstants.USER_ID_PV + "}")
	public ResponseEntity<ApiResponse> deleteUser(@Valid @PathVariable(AppConstants.USER_ID_PV) Integer userId) {
		
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.USER_DELETED_SUCCESSFULLY, true), HttpStatus.OK);
	} 
	
	//GET - GET USER.
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		
		//List<UserDto> allUsers = this.userService.getAllUsers();
		//return new ResponseEntity<List<UserDto>>(allUsers, HttpStatus.OK);
		
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	@GetMapping("/{" + AppConstants.USER_ID_PV + "}")
	public ResponseEntity<UserDto> getUser(@PathVariable(AppConstants.USER_ID_PV) Integer userId){
		
		//UserDto user = this.userService.getUserById(userId);
		//return new ResponseEntity<UserDto>(user, HttpStatus.OK);
		
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
}
