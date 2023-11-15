package com.api.blog.exceptions;

import com.api.blog.config.AppConstants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	String resourceName;
	String fieldName;
	long fieldValue;
	String resourceValue;
	
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
	
		super(String.format(AppConstants.RESOURCE_NOT_FOUND_CUSTOM_STRING, resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
	
		super(String.format(AppConstants.RESOURCE_NOT_FOUND_CUSTOM_STRING, resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.resourceValue = fieldValue;
	}
}