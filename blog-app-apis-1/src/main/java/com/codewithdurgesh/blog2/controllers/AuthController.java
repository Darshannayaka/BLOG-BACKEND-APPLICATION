package com.codewithdurgesh.blog2.controllers;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog2.entities.User;
import com.codewithdurgesh.blog2.exceptions.ApiException;
import com.codewithdurgesh.blog2.payloads.JwtAuthRequest;
import com.codewithdurgesh.blog2.payloads.JwtAuthResponse;
import com.codewithdurgesh.blog2.payloads.UserDto;
import com.codewithdurgesh.blog2.repositories.UserRepo;
import com.codewithdurgesh.blog2.security.JwtTokenHelper;
import com.codewithdurgesh.blog2.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@SecurityRequirement(name = "scheme1")
@RequestMapping("/api/v1/auth/")
@Tag(name = "AuthController", description = "REST API's Related To Perform Auth Operation !! ")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/generate-token")
	@Operation(summary = "Generate JWT Token To Access The Api")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception{
	this.authenticate(request.getUsername(), request.getPassword());	
	
	UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
	String token = this.jwtTokenHelper.generateToken(userDetails);
	JwtAuthResponse response = new JwtAuthResponse();
	response.setToken(token);
	response.setUser(this.mapper.map((User)userDetails, UserDto.class));
	return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		
		  try {
		this.authenticationManager.authenticate(authenticationToken);
		}catch (BadCredentialsException e) {
			System.out.println("Invalid Details !!");
			throw new ApiException("Invalid UserName or Password !!");
		}
		}
	
	//REGISTER-NEW-USER
	@PostMapping("/register")
	@Operation(summary = "Register New User")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto){
		UserDto registerdNewUser = this.userService.registerNewUser(userDto);
	return new ResponseEntity<UserDto>(registerdNewUser,HttpStatus.CREATED);
	}
	
	// get loggedin user data
		@Autowired
		private UserRepo userRepo;
		@Autowired
		private ModelMapper mapper;

		@GetMapping("/current-user/")
		public ResponseEntity<UserDto> getUser(Principal principal) {
			User user = this.userRepo.findByEmail(principal.getName()).get();
			return new ResponseEntity<UserDto>(this.mapper.map(user, UserDto.class), HttpStatus.OK);
		}

}
