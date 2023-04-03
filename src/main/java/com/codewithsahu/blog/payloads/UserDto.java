package com.codewithsahu.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.codewithsahu.blog.entities.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
	
	
	private int id;
	
	@NotEmpty
	@Size(min=3,message= "User must be minimum of 3 characters !!")
	private String name;
	
	@Email(message = "Email must be valid !!")
	private String email;
	
	@NotEmpty
	@Size(min=5,max=15,message = "Password must be minimum of 5 and maximum of 15 characters !!")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();

}
