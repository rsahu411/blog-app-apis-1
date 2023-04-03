package com.codewithsahu.blog.services;

import java.util.List;

import com.codewithsahu.blog.payloads.PostDto;
import com.codewithsahu.blog.payloads.PostResponse;

public interface PostService {
	
	// create post
		PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
		
		// update post by id
		PostDto updatePost(PostDto postDto,Integer postId);
		
		// delete Post by id
		void deletePostById(Integer postId);
		
		// Get post by id
		PostDto getPostById(Integer postId);
		
		// Get all posts
		PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
		
		// get posts by category
		PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize);
		
		// get posts by user
		PostResponse getPostsByUser(Integer userId,Integer pageNumber, Integer pageSize);
		
		// search posts
		List<PostDto> searchPostsByTitle(String keyword);

}
