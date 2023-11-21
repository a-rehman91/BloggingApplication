package com.api.blog.payloads;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.api.blog.entities.Comment;
import com.api.blog.entities.Post;
import com.api.blog.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
	
	@NotEmpty(message = "Name is required.")
	@Size(min = 3, message = "Username must be of 3 characters.")
	private String name;
	
	@NotEmpty(message = "Email is required.")
	@Email(message = "Email address is not valid.")
	private String email;
	
	@NotEmpty(message = "Password is required.")
	@Size(min = 5, message = "Password must be of 5 characters.")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	private String about;
	private Set<RoleDto> roles;

}
