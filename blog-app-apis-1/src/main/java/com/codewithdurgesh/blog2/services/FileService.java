package com.codewithdurgesh.blog2.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	//UPLOAD-THE-IMAGE
	String uploadImage(String path, MultipartFile file) throws IOException;
	
	//TO-SERVE-THE-IMAGE
	InputStream getResource(String path, String fileName) throws FileNotFoundException;

}
