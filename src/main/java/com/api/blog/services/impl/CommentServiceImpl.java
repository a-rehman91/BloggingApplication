package com.api.blog.services.impl;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.blog.config.AppConstants;
import com.api.blog.entities.Category;
import com.api.blog.entities.Comment;
import com.api.blog.entities.Post;
import com.api.blog.entities.User;
import com.api.blog.exceptions.ResourceNotFoundException;
import com.api.blog.payloads.CategoryDto;
import com.api.blog.payloads.CommentDto;
import com.api.blog.repositories.CommentRepo;
import com.api.blog.repositories.PostRepo;
import com.api.blog.repositories.UserRepo;
import com.api.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper; 
	
	@Override
	public CommentDto createCommentDto(CommentDto commentDto, int postId, int userId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.POST, AppConstants.POST_ID, postId));
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));
		
		Comment comment = dtoToComment(commentDto);
		comment.setPost(post);
		comment.setUser(user);
		
		return CommentToDto(this.commentRepo.save(comment));
	}

	@Override
	public void deleteComment(int commentId) {

		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.COMMENT, AppConstants.COMMENT_ID, commentId));
		this.commentRepo.delete(comment);
		
	}

	@Override
	public CommentDto getCommentById(int commentId) {

		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.COMMENT, AppConstants.COMMENT_ID, commentId));
		return CommentToDto(comment);
	}

	public Comment dtoToComment(CommentDto commentDto) {
		
		return modelMapper.map(commentDto, Comment.class);
	}

	public CommentDto CommentToDto(Comment comment) {

		return modelMapper.map(comment, CommentDto.class);
	}
}
