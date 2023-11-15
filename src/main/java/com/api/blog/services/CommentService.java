package com.api.blog.services;

import com.api.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createCommentDto(CommentDto commentDto, int postId, int userId);
	void deleteComment(int commentId);
	CommentDto getCommentById(int commentId);
}
