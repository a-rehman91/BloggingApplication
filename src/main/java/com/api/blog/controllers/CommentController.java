package com.api.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.blog.config.AppConstants;
import com.api.blog.payloads.ApiResponse;
import com.api.blog.payloads.CommentDto;
import com.api.blog.services.CommentService;

@RestController
@RequestMapping("/" + AppConstants.API)
public class CommentController {

	@Autowired
	CommentService commentService;
	
	@PostMapping("/" + AppConstants.USER_END_POINT + "/{" + AppConstants.USER_ID_PV + "}/" + AppConstants.POSTS_END_POINT + "/{" + AppConstants.POST_ID_PV + "}/" + AppConstants.COMMENTS_END_POINT)
	public ResponseEntity<CommentDto> createComment(@PathVariable(AppConstants.USER_ID_PV) Integer userId,
			@PathVariable(AppConstants.POST_ID_PV) Integer postId, 
			@RequestBody CommentDto commentDto) {

		CommentDto commentPosted = this.commentService.createCommentDto(commentDto, postId, userId);
		return new ResponseEntity<CommentDto>(commentPosted, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/" + AppConstants.COMMENTS_END_POINT + "/{" + AppConstants.COMMENT_ID_PV + "}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable(AppConstants.COMMENT_ID_PV) Integer commentId){
		
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.COMMENT_DELETED_SUCCESSFULLY, true), HttpStatus.OK);
	}
	
	@GetMapping("/" + AppConstants.COMMENTS_END_POINT + "/{" + AppConstants.COMMENT_ID_PV + "}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(AppConstants.COMMENT_ID_PV) Integer commentId){
		
		CommentDto comment = this.commentService.getCommentById(commentId);
		return ResponseEntity.ok(comment);
	}
}
