package com.codewithsahu.blog.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.codewithsahu.blog.config.AppConstants;
import com.codewithsahu.blog.payloads.ApiResponse;
import com.codewithsahu.blog.payloads.PostDto;
import com.codewithsahu.blog.payloads.PostResponse;
import com.codewithsahu.blog.services.FileService;
import com.codewithsahu.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	// create post
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId)
	{
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);

	}

	
	// update post by id
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId)
	{
		PostDto updatePostDto = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePostDto, HttpStatus.OK);
	}

	
	// delete post by id
	@DeleteMapping("/posts/{PostId}")
	public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer PostId)
	{
		this.postService.deletePostById(PostId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post is deleted successfully", true), HttpStatus.OK);
	}

	
	// get all posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pagesize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortdir)
	      
	{
		PostResponse postResponse = this.postService.getAllPost(pageNumber,pagesize,sortBy,sortdir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}

	
	// get post by id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostsById(@PathVariable Integer postId) {
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<>(postDto, HttpStatus.OK);
	}

	
	// get all posts category wise
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostsByCategory(
			@PathVariable Integer categoryId,
			@RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pagesize) {
		
		PostResponse posts = this.postService.getPostsByCategory(categoryId,pageNumber,pagesize);
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}

	
	// get all posts user wise
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostsByUser(
			@PathVariable Integer userId,
			@RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pagesize) {
		
		PostResponse postDtos = this.postService.getPostsByUser(userId,pageNumber,pagesize);
		return new ResponseEntity<PostResponse>(postDtos, HttpStatus.OK);
	}
	
	
	// search posts by title
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostsByTitle(@PathVariable("keywords")String keywords)
	{
		List<PostDto> searchedPosts = this.postService.searchPostsByTitle(keywords);
		return new ResponseEntity<List<PostDto>>(searchedPosts,HttpStatus.OK);
	}
	
	
	// Post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException
    {
    	PostDto postDto =this.postService.getPostById(postId);
    	String fileName = this.fileService.uploadImage(path, image);
	    postDto.setImageName(fileName);
	    PostDto updatePost = this.postService.updatePost(postDto, postId);
	    
	    return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
    }
	
	
	// Methods to serve files
	@GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable String imageName,HttpServletResponse response) throws IOException
	{
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
		
	}
}
