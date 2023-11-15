package com.api.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.blog.config.AppConstants;
import com.api.blog.payloads.ApiResponse;
import com.api.blog.payloads.CategoryDto;
import com.api.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/" + AppConstants.API + "/" + AppConstants.CATEGORIES)
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto){
		
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
	}
	
	@PutMapping("/{" + AppConstants.CATEGORY_ID_PV + "}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable(AppConstants.CATEGORY_ID_PV) Integer categoryId){
		
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);
	}
	
	@DeleteMapping("/{" + AppConstants.CATEGORY_ID_PV + "}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable(AppConstants.CATEGORY_ID_PV) Integer categoryId){
		
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.CATEGORY_DELETED_SUCCESSFULLY, true), HttpStatus.OK);
	}
	
	@GetMapping("/{" + AppConstants.CATEGORY_ID_PV + "}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(AppConstants.CATEGORY_ID_PV) Integer categoryId){
		
		CategoryDto category = this.categoryService.getCategoryById(categoryId);
		return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategories(){
		
		List<CategoryDto> categorires = this.categoryService.getAllCategory();
		return ResponseEntity.ok(categorires);
	}
}
