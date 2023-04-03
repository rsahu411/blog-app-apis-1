package com.codewithsahu.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotBlank
	@Size(min = 5, message = " Minimum size of category title is 5")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10, message = " Minimum size of categorry description is 10")
	private String categoryDescription;

}
