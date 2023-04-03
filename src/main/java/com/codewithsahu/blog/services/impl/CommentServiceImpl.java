package com.codewithsahu.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.codewithsahu.blog.entities.Comment;
import com.codewithsahu.blog.entities.Post;
import com.codewithsahu.blog.entities.User;
import com.codewithsahu.blog.exceptions.ResourceNotFoundException;
import com.codewithsahu.blog.payloads.CommentDto;
import com.codewithsahu.blog.payloads.CommentResponse;
import com.codewithsahu.blog.repositories.CommentRepo;
import com.codewithsahu.blog.repositories.PostRepo;
import com.codewithsahu.blog.repositories.UserRepo;
import com.codewithsahu.blog.services.CommentService;




@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	
	// Create Comment
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post id", postId));
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User id", userId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}
	
	
	
	
	// Update Comment by id
	@Override
	public CommentDto updateComment(CommentDto commentDto,Integer commentId) {
		
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment Id", commentId));
        comment.setContent(commentDto.getContent());
        
        Comment updatedComment = this.commentRepo.save(comment);
        
		return this.modelMapper.map(updatedComment, CommentDto.class);
	}
	
	
	
	// Get All Comments
	@Override
	public CommentResponse getAllComments(Integer pageNumber, Integer pageSize)
	{
		PageRequest pageable = PageRequest.of(pageNumber, pageSize);
		Page<Comment> comments = this.commentRepo.findAll(pageable);
		List<Comment> contentList = comments.getContent();
		List<CommentDto> commentDtos = contentList.stream().map((comment)-> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
		
		CommentResponse commentResponse = new CommentResponse();
		commentResponse.setPageNumber(comments.getNumber());
		commentResponse.setPageSize(comments.getSize());
		commentResponse.setTotalElements(comments.getTotalElements());
		commentResponse.setTotalPages(comments.getTotalPages());
		commentResponse.setLastPage(comments.isLast());
		commentResponse.setContent(commentDtos);
		return commentResponse;
	}
	
	
	
	
	// Get Comments by id
	@Override
	public CommentDto getCommentById(Integer commentId) {
		
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment Id", commentId));
		return this.modelMapper.map(comment, CommentDto.class);
	}
	
	
	

	// Delete Comments by id
	@Override
	public void deleteComment(Integer commentId) {
		
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment id", commentId));
		this.commentRepo.delete(comment);	
	}

}
