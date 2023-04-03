package com.codewithsahu.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithsahu.blog.payloads.ApiResponse;
import com.codewithsahu.blog.payloads.UserDto;
import com.codewithsahu.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	
	// POST- create user
	
	@PostMapping
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto createUserDto = this.service.createUser(userDto);
//		HttpStatus httpStatus = null;
//		new ResponseEntity(HttpStatus.OK);
//		 return ResponseEntity.ok(createUserDto);
		 
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	// PUT- update user
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Integer id)
	{
		UserDto updateUserDto = this.service.updateUser(userDto,id);
		return ResponseEntity.ok(updateUserDto);
	}
	
	
	// GET- user get
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer id)
	{
		UserDto getUserDto = this.service.getUserById(id);
		return ResponseEntity.ok(getUserDto);
	}
	
	
	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUser()
	{
		return ResponseEntity.ok(this.service.getAllUsers());
	}
	
	// Admin access
	// DELETE- delete user
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteuser(@PathVariable Integer userId )
	{
		this.service.deleteUser(userId);
		
		//return ResponseEntity.ok(Map.of("message","User deleted Successfully"));
		//return new ResponseEntity(Map.of("message","User Deleted Successfully"),HttpStatus.OK); 
		return  new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully",true),HttpStatus.OK);
	}

}
