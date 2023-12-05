package com.codewithdurgesh.blog2.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog2.config.AppConstants;
import com.codewithdurgesh.blog2.entities.Role;
import com.codewithdurgesh.blog2.entities.User;
import com.codewithdurgesh.blog2.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog2.exceptions.UserAlreadyExistsException;
import com.codewithdurgesh.blog2.payloads.UserDto;
import com.codewithdurgesh.blog2.repositories.RoleRepo;
import com.codewithdurgesh.blog2.repositories.UserRepo;
import com.codewithdurgesh.blog2.services.UserService;



@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
//	@Override
//	public UserDto createUser(UserDto userDto) {
//		User user = this.dtoToUser(userDto);
//		User saveUser = this.userRepo.save(user);
//		return this.userToDto(saveUser);
//	}
	 @Override
	    public UserDto createUser(UserDto userDto) {
	        // Check if the user already exists
	        if (userRepo.findByEmail(userDto.getEmail()).isPresent()) {
	            throw new UserAlreadyExistsException("User ", userDto.getEmail(), "Create New User");
	        }

	        User user = this.dtoToUser(userDto);
	        User saveUser = this.userRepo.save(user);
	        return this.userToDto(saveUser);
	    }

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		
		 if (userRepo.findByEmail(userDto.getEmail()).isPresent()) {
	            throw new UserAlreadyExistsException("User ", userDto.getEmail(), "Create New User");
	        }
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		User updatedUser = this.userRepo.save(user);
		
		UserDto userDto1 = this.userToDto(updatedUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User  user =  this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllusers() {
			List<User> users = this.userRepo.findAll();
			List<UserDto> allusers = users.stream().map(user-> userToDto(user)).collect(Collectors.toList());
		return allusers;
	}

//	@Override
//	public void DeleteUser(Integer userId) {
//		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
//		this.userRepo.delete(user);
//	}
	
	@Override
	public void DeleteUser(Integer userId) {
	    User user = this.userRepo.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

	    // Remove roles from the user before deleting
	    user.getRoles().clear();

	    // Save the user to update the changes (remove roles)
	    this.userRepo.save(user);

	    // Now you can safely delete the user
	    this.userRepo.delete(user);
	}

	
	
	
	//to convert DTO to User
	public User dtoToUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		return user;
	}
	
	
	
	//to convert User to DTO
	public UserDto userToDto(User user) {

	UserDto userDto = this.modelMapper.map(user, UserDto.class);
//	userDto.setId(user.getId());
//	userDto.setName(user.getName());
//	userDto.setEmail(user.getEmail());
//	userDto.setAbout(user.getAbout());
//	userDto.setPassword(user.getPassword());
	return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		// Check if the user already exists
        if (userRepo.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User ", userDto.getEmail(), "Create New User");
        }
		
		User user = this.modelMapper.map(userDto, User.class);
		//password-encoded
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		//set-roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User newRegisterUser = this.userRepo.save(user);
		return this.modelMapper.map(newRegisterUser, UserDto.class);
	}

}
