package com.codewithsahu.blog.payloads;

import java.util.*;

import com.codewithsahu.blog.entities.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private Integer postId;
	
	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	private UserDto user ;
	
	private List<CommentDto> comments = new ArrayList<>();
	

}
