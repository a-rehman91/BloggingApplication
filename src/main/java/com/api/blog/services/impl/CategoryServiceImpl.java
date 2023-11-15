package com.api.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.blog.config.AppConstants;
import com.api.blog.entities.Category;
import com.api.blog.exceptions.ResourceNotFoundException;
import com.api.blog.payloads.CategoryDto;
import com.api.blog.repositories.CategoryRepo;
import com.api.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto category) {

		return categoryToDto(this.categoryRepo.save(dtoToCategory(category)));
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {

		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));
		
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());
		
		return categoryToDto(this.categoryRepo.save(category));
	}

	@Override
	public CategoryDto getCategoryById(int categoryId) {

		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));
		return categoryToDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategory() {

		List<Category> categories = this.categoryRepo.findAll();
		return categories.stream().map(category->categoryToDto(category)).collect(Collectors.toList());
	}

	@Override
	public void deleteCategory(int categoryId) {
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));
		this.categoryRepo.delete(category);
	}
	
	public Category dtoToCategory(CategoryDto categoryDto) {
		
		return modelMapper.map(categoryDto, Category.class);
	}

	public CategoryDto categoryToDto(Category category) {

		return modelMapper.map(category, CategoryDto.class);
	}
	
}
