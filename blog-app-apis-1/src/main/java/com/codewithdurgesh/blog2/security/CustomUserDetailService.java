package com.codewithdurgesh.blog2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog2.entities.User;
import com.codewithdurgesh.blog2.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog2.repositories.UserRepo;


@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//LOAD-USER-FROM-DATABASE-BY-USERNAME
		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User", "email :"+username,404));
		
		return user;
	}

}
