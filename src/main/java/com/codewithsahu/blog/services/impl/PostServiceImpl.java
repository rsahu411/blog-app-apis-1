package com.codewithsahu.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithsahu.blog.config.AppConstants;
import com.codewithsahu.blog.entities.Category;
import com.codewithsahu.blog.entities.Post;
import com.codewithsahu.blog.entities.User;
import com.codewithsahu.blog.exceptions.ResourceNotFoundException;
import com.codewithsahu.blog.payloads.PostDto;
import com.codewithsahu.blog.payloads.PostResponse;
import com.codewithsahu.blog.repositories.CategoryRepo;
import com.codewithsahu.blog.repositories.PostRepo;
import com.codewithsahu.blog.repositories.UserRepo;
import com.codewithsahu.blog.services.PostService;


@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	// create post
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	// update post by id
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));

		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());

		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	// delete post by id
	@Override
	public void deletePostById(Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));
		this.postRepo.delete(post);
	}

	// get post by id
	public PostDto getPostById(Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post id ", postId));

		return this.modelMapper.map(post, PostDto.class);
	}

	// get all posts
	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort = null;
		if(sortDir.equalsIgnoreCase(AppConstants.SORT_DIR))
			sort = Sort.by(sortBy).ascending();
	    else
	    	sort = Sort.by(sortBy).descending();
		

	    Pageable pageable =PageRequest.of(pageNumber, pageSize, sort);
	    Page<Post> getAllPosts = this.postRepo.findAll(pageable);
	    List<Post> contentList = getAllPosts.getContent();
		List<PostDto> postDtos = contentList.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setPageNumber(getAllPosts.getNumber());
		postResponse.setPageSize(getAllPosts.getSize());
		postResponse.setTotalElements(getAllPosts.getTotalElements());
		postResponse.setTotalPages(getAllPosts.getTotalPages());
		postResponse.setLastPage(getAllPosts.isLast());
		postResponse.setContent(postDtos);
		
		return postResponse;
	}

	@Override
	// get posts by category wise
	public PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Post> getAllPosts = this.postRepo.findByCategory(category,pageable);
		List<Post> contentList = getAllPosts.getContent();
		List<PostDto> postDtos = contentList.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setPageNumber(getAllPosts.getNumber());
		postResponse.setPageSize(getAllPosts.getSize());
		postResponse.setTotalElements(getAllPosts.getTotalElements());
		postResponse.setTotalPages(getAllPosts.getTotalPages());
		postResponse.setLastPage(getAllPosts.isLast());
		postResponse.setContent(postDtos);
		
		return postResponse;
	}

	@Override
	// get posts by user wise
	public PostResponse getPostsByUser(Integer userId,Integer pageNumber, Integer pageSize) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Post> getAllPosts = this.postRepo.findByUser(user,pageable);
		List<Post> contentList = getAllPosts.getContent();
		
		List<PostDto> postDtos = contentList.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setPageNumber(getAllPosts.getNumber());
		postResponse.setPageSize(getAllPosts.getSize());
		postResponse.setTotalElements(getAllPosts.getTotalElements());
		postResponse.setTotalPages(getAllPosts.getTotalPages());
		postResponse.setLastPage(getAllPosts.isLast());
		postResponse.setContent(postDtos);

		return postResponse;
	}

	@Override
	// search posts
	public List<PostDto> searchPostsByTitle(String keyword) {
		List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> postsDtos = posts.stream()
				.map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

		return postsDtos;
	}

}
