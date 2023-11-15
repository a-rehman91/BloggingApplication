package com.api.blog.services;

import java.util.List;

import com.api.blog.payloads.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto category);
	CategoryDto updateCategory(CategoryDto category, int userId);
	CategoryDto getCategoryById(int categoryId);
	List<CategoryDto> getAllCategory();
	void deleteCategory(int categoryId);	
}
