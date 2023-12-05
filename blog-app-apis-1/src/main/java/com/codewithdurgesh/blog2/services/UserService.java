package com.codewithdurgesh.blog2.services;

import java.util.List;

import com.codewithdurgesh.blog2.payloads.UserDto;


public interface UserService {

	UserDto registerNewUser(UserDto userDto);
	
	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user, Integer userId);

	UserDto getUserById(Integer userId);

	List<UserDto> getAllusers();

	void DeleteUser(Integer userId);

}
