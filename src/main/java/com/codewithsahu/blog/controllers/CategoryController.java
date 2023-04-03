package com.codewithsahu.blog.controllers;

import java.util.List;

import javax.validation.Valid;

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

import com.codewithsahu.blog.payloads.ApiResponse;
import com.codewithsahu.blog.payloads.CategoryDto;
import com.codewithsahu.blog.services.CategoriesService;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoriesService service;
	
	
	// create category
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto saveCategoryDto = this.service.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(saveCategoryDto,HttpStatus.CREATED);
	}
	
	
	// update category by id
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId)
	{
		CategoryDto updateCategoryDto = this.service.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(updateCategoryDto,HttpStatus.OK);
	}
	
	
	// delete category by id
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId)
	{
		this.service.deleteCatogory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category is Deleted Sucessfully ",true),HttpStatus.OK);
	}
	
	
	// get Category by id
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId)
	{
		CategoryDto categoryDto = this.service.getCategoryById(categoryId);
		return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
	}
	
	
	// get all categories 
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategories()
	{
		List<CategoryDto> categoryDtos = this.service.getAllCategories();
		return ResponseEntity.ok(categoryDtos);
		//return new ResponseEntity<List<CategoryDto>>(categoryDtos,HttpStatus.OK);
	}
	
	
	

}
