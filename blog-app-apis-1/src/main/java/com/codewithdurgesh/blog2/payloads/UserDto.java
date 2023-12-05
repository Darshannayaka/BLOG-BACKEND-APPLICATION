package com.codewithdurgesh.blog2.payloads;



import java.util.HashSet;
import java.util.Set;

import com.codewithdurgesh.blog2.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
	
	@NotEmpty
//	@Size(min = 4, message = "Username must be min of 4 charaters !!")
	@Pattern(regexp = "[a-zA-Z]{4,12}$", message = "username must be 4 to 12 length with no special characters")
	private String name;
	
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "Email is not valid")
	private String email;
	
	@NotEmpty
//	@Size(min = 4, max = 12, message = "Password must be min of 3 chars and max of 12 chars !!")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{4,12}$", message = "password must be min 4 and max 12 length containing at least 1 uppercase, 1 lowercase, 1 special character and 1 digit")
	private String password;
	
	@NotEmpty
	private String about;
	
	
	private Set<RoleDto> roles =  new HashSet<>();
	
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		 this.password=password;
	}
	
	
}
