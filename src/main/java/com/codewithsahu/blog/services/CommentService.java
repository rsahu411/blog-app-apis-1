package com.codewithsahu.blog.services;


import com.codewithsahu.blog.payloads.CommentDto;
import com.codewithsahu.blog.payloads.CommentResponse;


public interface CommentService {
	
	
	// Create Comment
	CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);
	
	// Update Comment by id
	CommentDto updateComment(CommentDto commentDto, Integer commentId);
	
	// Get All Comments
	CommentResponse getAllComments(Integer pageNumber,Integer pageSize);
	
	// Get Comment By Id
	CommentDto getCommentById(Integer commentId);
	
	// Delete Comment By id
	void deleteComment(Integer commentId);

}
