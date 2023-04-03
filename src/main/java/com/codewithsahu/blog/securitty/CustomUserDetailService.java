package com.codewithsahu.blog.securitty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codewithsahu.blog.entities.User;
import com.codewithsahu.blog.repositories.UserRepo;


@Service
public class CustomUserDetailService implements UserDetailsService {

	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// loading user from database by username
		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Username is not found "));
		
		return user;
	}

}
