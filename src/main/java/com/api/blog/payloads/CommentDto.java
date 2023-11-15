package com.api.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.api.blog.entities.Post;
import com.api.blog.entities.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDto {

	private int id;
	private String content;
}
