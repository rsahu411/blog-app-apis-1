package com.codewithsahu.blog.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codewithsahu.blog.config.AppConstants;
import com.codewithsahu.blog.payloads.ApiResponse;
import com.codewithsahu.blog.payloads.CommentDto;
import com.codewithsahu.blog.payloads.CommentResponse;
import com.codewithsahu.blog.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	
	// Create Comments
	@PostMapping("/user/{userId}/post/{postId}/comments")
	public ResponseEntity<CommentDto> creatComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId,@PathVariable Integer userId)
	{
		CommentDto createComment = this.commentService.createComment(commentDto, postId,userId);
		return  new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
	}
	
	
	
	
	// Update Comments
	@PutMapping("/comments/{commentId}")
	public ResponseEntity<CommentDto> UpgateComment(@RequestBody CommentDto commentDto,@PathVariable Integer commentId)
	{
		CommentDto updatedCommentDto = this.commentService.updateComment(commentDto, commentId);
		return new ResponseEntity<CommentDto>(updatedCommentDto,HttpStatus.OK);
	}
	
	
	
	// Get All Comments
	@GetMapping("/comments")
	public ResponseEntity<CommentResponse> getAllComments(
			@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGE_SIZE) Integer pageSize)
	{
		CommentResponse commentResponse = this.commentService.getAllComments(pageNumber, pageSize);
		
		return new ResponseEntity<CommentResponse>(commentResponse,HttpStatus.OK);
	}
	
	
	
	
	// Get comments By id
	@GetMapping("/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable Integer commentId)
	{
		CommentDto commentDto = this.commentService.getCommentById(commentId);
		return new ResponseEntity<CommentDto>(commentDto,HttpStatus.OK);
	}
	
	
	
	// Delete comment by id
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment( Integer commentId)
	{
		this.commentService.deleteComment(commentId);
		return  new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted Successfully",true),HttpStatus.OK);
	}

}
