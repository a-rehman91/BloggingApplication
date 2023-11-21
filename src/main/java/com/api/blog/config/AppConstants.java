package com.api.blog.config;

public class AppConstants {

	public static final String PAGE_NUMBER = "page_number";
	public static final String PAGE_SIZE = "page_size";
	public static final String SORT_BY = "sort_by";
	public static final String SORT_DIR = "sort_dir";
	public static final String PAGE_NUMBER_DEFAULT_VALUE = "0";
	public static final String PAGE_SIZE_DEFAULT_VALUE = "15";
	public static final String SORT_BY_DEFAULT_VALUE = "id";
	public static final String SORT_DIR_DEFAULT_VALUE = "acs";
	public static final boolean FLAG_FLASE = false;
	public static final boolean FLAG_TRUE = true;

	public static final String CATEGORY_DELETED_SUCCESSFULLY = "Category Deleted Successfully";
	public static final String USER_DELETED_SUCCESSFULLY = "User deleted Successfully";
	public static final String RESOURCE_NOT_FOUND_CUSTOM_STRING = "%s not found with %s : %s";

	public static final String CATEGORY = "CATEGORY";
	public static final String CATEGORY_ID = " category id ";

	public static final String USER = "USER";
	public static final String USER_ID = " user id ";

	public static final String POST = "POST";
	public static final String POST_ID = " post id ";

	public static final String COMMENT = "COMMENT";
	public static final String COMMENT_ID = " comment id ";

	public static final String COMMENT_DELETED_SUCCESSFULLY = "Comment deleted Successfully";

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	public static final String JWT_TOKEN_KEY = "jwtTokenKey";

	public static final String ADMIN_USER = "ROLE_ADMIN";
	public static final String NORMAL_USER = "ROLE_NORMAL";
	public static final Integer ADMIN_ROLE = 501;
	public static final Integer NORMAL_ROLE = 502;

	public static final String[] PUBLIC_URL = { "/api/auth/**" };
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String BEARER = "Bearer";
	
	public static final String API = "api";
	public static final String USERS = "users";
	public static final String USER_ID_PV = "user_id";
	public static final String AUTH = "auth";
	public static final String LOGIN = "login";
	public static final String REGISTER = "register";
	public static final String CATEGORIES = "categories";
	public static final String CATEGORY_ID_PV = "category_id";
	public static final String USER_END_POINT = "user";
	public static final String CATEGORY_END_POINT = "category";
	public static final String POSTS_END_POINT = "posts";
	public static final String POST_ID_PV = "post_id";
	public static final String SEARCH_END_POINT = "search";
	public static final String KEYWORD_PV = "keyword";
	public static final String IMAGE_END_POINT = "image";
	public static final String UPLOAD_END_POINT = "upload";
	public static final String IMAGE_NAME_PV = "image_name";
	public static final String MULTI_UPLOAD_PV = "multi-upload";
	public static final String COMMENTS_END_POINT = "comments";
	public static final String COMMENT_ID_PV = "comment_id";
	
		
}
