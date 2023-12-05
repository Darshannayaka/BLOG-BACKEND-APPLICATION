package com.codewithdurgesh.blog2.controllers;

import java.util.List;


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

import com.codewithdurgesh.blog2.payloads.ApiResponse;
import com.codewithdurgesh.blog2.payloads.UserDto;
import com.codewithdurgesh.blog2.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@SecurityRequirement(name = "scheme1")
@RequestMapping("/api/v1/users")
@Tag(name = "UserController", description = "REST API's Related To Perform User Operation !! ")
public class UserController {

	@Autowired
	private UserService userService;
	
	//POST- CREATE-USER
	@PostMapping("/")
	@Operation(summary = "Create Users")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto = this.userService.createUser(userDto);
	return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	
	//PUT - UPDATE USER
	@PutMapping("/{userId}")
	@Operation(summary = "Update Users")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uid ){
		UserDto updateUser = this.userService.updateUser(userDto, uid);
	return ResponseEntity.ok(updateUser);
	}

	//DELETE - DELETE USER
	//ONLY-ADMIN-ACCESS
	
	@Operation(summary = "Delete Users")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid){
		this.userService.DeleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true),HttpStatus.OK);
	}
	
	//GET - ALL USERS
	@GetMapping("/")
	@Operation(summary = "Get All Users")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userService.getAllusers());
	}
	
	// GET - USER-BY-Id
	@GetMapping("/{userId}")
	@Operation(summary = "Get Single User")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
}
