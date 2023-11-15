package com.api.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.api.blog.entities.Category;
import com.api.blog.entities.Comment;
import com.api.blog.entities.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Setter
@Getter
public class PostDto {

	private int id;
	
	@NotEmpty
	@Size(min=1, message = "Title should be 1 character.")
	private String title;
	private String content;
	private String image;
	private Date date;
	private CategoryDto category;
	private UserDto user;
	private Set<CommentDto> comments;
}
