package com.codewithdurgesh.blog2.exceptions;

@SuppressWarnings("serial")
public class UserAlreadyExistsException extends RuntimeException {
   
	
	String resourceName;
	String fieldName;
	String fieldValue;
	public UserAlreadyExistsException(String resourceName, String fieldName, String fieldValue) {
		super(String.format("%s Already Exists %s: %s", resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	
}
