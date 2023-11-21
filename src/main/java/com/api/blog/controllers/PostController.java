package com.api.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.blog.config.AppConstants;
import com.api.blog.payloads.ApiResponse;
import com.api.blog.payloads.PostDto;
import com.api.blog.payloads.PostResponse;
import com.api.blog.services.FileService;
import com.api.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/" + AppConstants.API)
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	@PostMapping("/" + AppConstants.USER_END_POINT + "/{" + AppConstants.USER_ID_PV + "}/"
			+ AppConstants.CATEGORY_END_POINT + "/{" + AppConstants.CATEGORY_ID_PV + "}/"
			+ AppConstants.POSTS_END_POINT)
	public ResponseEntity<PostDto> createPost(@PathVariable(AppConstants.USER_ID_PV) Integer userId,
			@PathVariable(AppConstants.CATEGORY_ID_PV) Integer categoryId, @RequestBody PostDto postDto) {

		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}

	@GetMapping("/" + AppConstants.POSTS_END_POINT + "/{" + AppConstants.POST_ID_PV + "}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(AppConstants.POST_ID_PV) Integer postId) {

		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}

	@GetMapping("/" + AppConstants.POSTS_END_POINT)
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = AppConstants.PAGE_NUMBER, defaultValue = AppConstants.PAGE_NUMBER_DEFAULT_VALUE, required = AppConstants.FLAG_FLASE) Integer pageNumber,
			@RequestParam(value = AppConstants.PAGE_SIZE, defaultValue = AppConstants.PAGE_SIZE_DEFAULT_VALUE, required = AppConstants.FLAG_FLASE) Integer pageSize,
			@RequestParam(value = AppConstants.SORT_BY, defaultValue = AppConstants.SORT_BY_DEFAULT_VALUE, required = AppConstants.FLAG_FLASE) String sortBy,
			@RequestParam(value = AppConstants.SORT_DIR, defaultValue = AppConstants.SORT_DIR_DEFAULT_VALUE, required = AppConstants.FLAG_FLASE) String sortDirection) {

		PostResponse allPosts = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDirection);
		return new ResponseEntity<PostResponse>(allPosts, HttpStatus.OK);
	}

	@GetMapping("/" + AppConstants.POSTS_END_POINT + "/" + AppConstants.SEARCH_END_POINT + "/{"
			+ AppConstants.KEYWORD_PV + "}")
	public ResponseEntity<PostResponse> searchPostByTitle(@PathVariable("keyword") String keyword,
			@RequestParam(value = AppConstants.PAGE_NUMBER, defaultValue = AppConstants.PAGE_NUMBER_DEFAULT_VALUE, required = AppConstants.FLAG_FLASE) Integer pageNumber,
			@RequestParam(value = AppConstants.PAGE_SIZE, defaultValue = AppConstants.PAGE_SIZE_DEFAULT_VALUE, required = AppConstants.FLAG_FLASE) Integer pageSize,
			@RequestParam(value = AppConstants.SORT_BY, defaultValue = AppConstants.SORT_BY_DEFAULT_VALUE, required = AppConstants.FLAG_FLASE) String sortBy,
			@RequestParam(value = AppConstants.SORT_DIR, defaultValue = AppConstants.SORT_DIR_DEFAULT_VALUE, required = AppConstants.FLAG_FLASE) String sortDirection) {

		PostResponse allPosts = this.postService.searchPosts(keyword, pageNumber, pageSize, sortBy, sortDirection);
		return new ResponseEntity<PostResponse>(allPosts, HttpStatus.OK);
	}

	@GetMapping("/" + AppConstants.CATEGORY_END_POINT + "/{" + AppConstants.CATEGORY_ID_PV + "}/"
			+ AppConstants.POSTS_END_POINT)
	public ResponseEntity<PostResponse> getPostByCategoryId(@PathVariable("category_id") Integer categoryId,
			@RequestParam(value = AppConstants.PAGE_NUMBER, defaultValue = AppConstants.PAGE_NUMBER_DEFAULT_VALUE, required = AppConstants.FLAG_FLASE) Integer pageNumber,
			@RequestParam(value = AppConstants.PAGE_SIZE, defaultValue = AppConstants.PAGE_SIZE_DEFAULT_VALUE, required = AppConstants.FLAG_FLASE) Integer pageSize,
			@RequestParam(value = AppConstants.SORT_BY, defaultValue = AppConstants.SORT_BY_DEFAULT_VALUE, required = AppConstants.FLAG_FLASE) String sortBy,
			@RequestParam(value = AppConstants.SORT_DIR, defaultValue = AppConstants.SORT_DIR_DEFAULT_VALUE, required = AppConstants.FLAG_FLASE) String sortDirection) {

		PostResponse postsByCategory = this.postService.getPostsByCategory(categoryId, pageNumber, pageSize, sortBy,
				sortDirection);
		return ResponseEntity.ok(postsByCategory);
	}

	@GetMapping("/" + AppConstants.USER_END_POINT + "/{" + AppConstants.USER_ID_PV + "}/"
			+ AppConstants.POSTS_END_POINT)
	public ResponseEntity<PostResponse> getPostByUserId(@PathVariable("user_id") Integer userId,
			@RequestParam(value = AppConstants.PAGE_NUMBER, defaultValue = AppConstants.PAGE_NUMBER_DEFAULT_VALUE, required = AppConstants.FLAG_FLASE) Integer pageNumber,
			@RequestParam(value = AppConstants.PAGE_SIZE, defaultValue = AppConstants.PAGE_SIZE_DEFAULT_VALUE, required = AppConstants.FLAG_FLASE) Integer pageSize,
			@RequestParam(value = AppConstants.SORT_BY, defaultValue = AppConstants.SORT_BY_DEFAULT_VALUE, required = AppConstants.FLAG_FLASE) String sortBy,
			@RequestParam(value = AppConstants.SORT_DIR, defaultValue = AppConstants.SORT_DIR_DEFAULT_VALUE, required = AppConstants.FLAG_FLASE) String sortDirection) {

		PostResponse postsByUser = this.postService.getPostsByUser(userId, pageNumber, pageSize, sortBy, sortDirection);
		return ResponseEntity.ok(postsByUser);
	}

	@DeleteMapping("/" + AppConstants.POSTS_END_POINT + "/{" + AppConstants.POST_ID_PV + "}")
	public ResponseEntity<ApiResponse> deletePostById(@PathVariable(AppConstants.POST_ID_PV) Integer postId) {

		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully", true), HttpStatus.OK);
	}

	@PutMapping("/" + AppConstants.POSTS_END_POINT + "/{" + AppConstants.POST_ID_PV + "}")
	public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto,
			@PathVariable("post_id") Integer postId) {

		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	@PostMapping("/" + AppConstants.POSTS_END_POINT + "/" + AppConstants.IMAGE_END_POINT + "/"
			+ AppConstants.UPLOAD_END_POINT + "/{" + AppConstants.POST_ID_PV + "}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile file,
			@PathVariable("post_id") Integer postId) throws IOException {

		PostDto postDto = this.postService.getPostById(postId);
		System.out.println("Title of the post: "+postDto.getTitle());
		String uploadedFileName = this.fileService.uploadImage(path, file);
		postDto.setImage(uploadedFileName);
		System.out.println("filename:"+uploadedFileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);

		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	@GetMapping(value = AppConstants.POSTS_END_POINT + "/" + AppConstants.IMAGE_END_POINT + "/{"
			+ AppConstants.IMAGE_NAME_PV + "}", produces = MediaType.ALL_VALUE)
	public void downloadImage(@PathVariable(AppConstants.IMAGE_NAME_PV) String imageName, HttpServletResponse response)
			throws IOException {

		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.ALL_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());

	}

	@PostMapping("/" + AppConstants.POSTS_END_POINT + "/" + AppConstants.IMAGE_END_POINT + "/"
			+ AppConstants.MULTI_UPLOAD_PV)
	public ResponseEntity<?> uploadMultipleFiles(@RequestParam(AppConstants.IMAGE_END_POINT) MultipartFile[] files) {

		Arrays.stream(files).forEach(file -> {
			try {
				this.fileService.uploadImage(path, file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return ResponseEntity.ok("file uploaded");
	}
}
