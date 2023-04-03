package com.codewithsahu.blog.services;

import java.util.List;

import com.codewithsahu.blog.entities.Category;
import com.codewithsahu.blog.payloads.CategoryDto;

public interface CategoriesService  {
	
	// create category
	CategoryDto createCategory(CategoryDto categoryDto);
	
	// update category by id
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	// delete category by id
	void deleteCatogory(Integer categoryId);
	
	// get category by id
    CategoryDto getCategoryById(Integer categoryId);
    
    //get all categories
    List<CategoryDto> getAllCategories();
}
