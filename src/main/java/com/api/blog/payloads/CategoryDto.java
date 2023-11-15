package com.api.blog.payloads;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private int id;
	
	@NotEmpty
	@Size(min = 1, message = "Title should be 1 character.")
	private String title;
	
	@Nullable
	private String description;
	
}
