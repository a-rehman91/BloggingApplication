package com.api.blog.services;

import java.util.List;

import com.api.blog.payloads.PostDto;
import com.api.blog.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto post, int userId, int categoryId);
	PostDto updatePost(PostDto post, int postId);
	PostDto getPostById(int postId);
	PostResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDirection);
	void deletePost(int postId);	
	PostResponse getPostsByCategory(int categoryId, int pageNumber, int pageSize, String sortBy, String sortDirection);
	PostResponse getPostsByUser(int userId, int pageNumber, int pageSize, String sortBy, String sortDirection);
	PostResponse searchPosts(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection);
}
