package com.api.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.api.blog.config.AppConstants;
import com.api.blog.entities.Category;
import com.api.blog.entities.Post;
import com.api.blog.entities.User;
import com.api.blog.exceptions.ResourceNotFoundException;
import com.api.blog.payloads.PostDto;
import com.api.blog.payloads.PostResponse;
import com.api.blog.repositories.CategoryRepo;
import com.api.blog.repositories.PostRepo;
import com.api.blog.repositories.UserRepo;
import com.api.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto, int userId, int categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));
		Post post = dtoToPost(postDto);
		
		if(post.getImage() == null)
			post.setImage("default.png");
		
		post.setDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		return postToDto(this.postRepo.save(post));
	}

	@Override
	public PostDto updatePost(PostDto postDto, int postId) {

		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.POST, AppConstants.POST_ID, postId));

		int userId = post.getUser().getId();
		int categoryId = post.getCategory().getId();
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setDate(new Date());
		
		if(postDto.getImage() == null)
			post.setImage("default.png");
		else
			post.setImage(postDto.getImage());
		
		post.setUser(user);
		post.setCategory(category);
		
		return postToDto(this.postRepo.save(post));
	}

	@Override
	public PostDto getPostById(int postId) {
		
		return postToDto(this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException(AppConstants.POST, AppConstants.POST_ID, postId)));
	}

	@Override
	public PostResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDirection) {
				
		Sort sort = sortDirection.equalsIgnoreCase("acs")? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
				
		Pageable of = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(of);
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(pagePost.getContent().stream().map(post->this.postToDto(post)).collect(Collectors.toList()));
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	@Override
	public void deletePost(int postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.POST, AppConstants.POST_ID, postId));
		this.postRepo.delete(post);
		
	}

	@Override
	public PostResponse getPostsByCategory(int categoryId, int pageNumber, int pageSize, String sortBy, String sortDirection) {

		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));
		
		Sort sort = sortDirection.equalsIgnoreCase("acs")? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePostsByCategory = this.postRepo.findByCategory(category, pageable);
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(pagePostsByCategory.stream().map(post->this.postToDto(post)).collect(Collectors.toList()));
		postResponse.setPageNumber(pagePostsByCategory.getNumber());
		postResponse.setPageSize(pagePostsByCategory.getSize());
		postResponse.setTotalElements(pagePostsByCategory.getTotalElements());
		postResponse.setTotalPages(pagePostsByCategory.getTotalPages());
		postResponse.setLastPage(pagePostsByCategory.isLast());
		
		
		return postResponse;
	}

	@Override
	public PostResponse getPostsByUser(int userId, int pageNumber, int pageSize, String sortBy, String sortDirection) {		

		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));

		Sort sort = sortDirection.equalsIgnoreCase("acs")? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePostByUser = this.postRepo.findByUser(user, pageable);
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(pagePostByUser.getContent().stream().map(post->this.postToDto(post)).collect(Collectors.toList()));
		postResponse.setPageNumber(pagePostByUser.getNumber());
		postResponse.setPageSize(pagePostByUser.getSize());
		postResponse.setTotalElements(pagePostByUser.getTotalElements());
		postResponse.setTotalPages(pagePostByUser.getTotalPages());
		postResponse.setLastPage(pagePostByUser.isLast());
 
		return postResponse;
	}

	@Override
	public PostResponse searchPosts(String keyword, int pageNumber, int pageSize, String sortBy, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("acs")? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePosts = this.postRepo.findByTitleContaining(keyword, pageable);
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(pagePosts.getContent().stream().map(post->this.postToDto(post)).collect(Collectors.toList()));
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		
		return postResponse;
	}

	public Post dtoToPost(PostDto postDto) {
		
		return this.modelMapper.map(postDto, Post.class);
	}

	public PostDto postToDto(Post post) {

		return this.modelMapper.map(post, PostDto.class);
	}
	
}
