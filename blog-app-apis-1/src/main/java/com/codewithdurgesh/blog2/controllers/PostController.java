package com.codewithdurgesh.blog2.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codewithdurgesh.blog2.config.AppConstants;
import com.codewithdurgesh.blog2.payloads.ApiResponse;
import com.codewithdurgesh.blog2.payloads.PostDto;
import com.codewithdurgesh.blog2.payloads.PostResponse;
import com.codewithdurgesh.blog2.services.FileService;
import com.codewithdurgesh.blog2.services.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@SecurityRequirement(name = "scheme1")
@RequestMapping("/api/v1/")
@Tag(name = "PostController", description = "REST API's Related To Perform Post Operation !! ")
public class PostController {

	
	@Autowired
	private PostService postService;
	
	private final FileService fileService;

    @Autowired
    public PostController(FileService fileService) {
        this.fileService = fileService;
    }
	
	@Value("${project.image}")
	private String path;
	
	//CREATE
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	@Operation(summary = "Create Posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
			@PathVariable Integer userId, @PathVariable Integer categoryId){
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	//GET-POST-BY-CATEGORY_ID
	@GetMapping("/category/{categoryId}/posts")
	@Operation(summary = "Get Single Post")
	public ResponseEntity<List<PostDto>> getPostByCategoryId(@PathVariable Integer categoryId){
		return ResponseEntity.ok(this.postService.getPostBycategoryId(categoryId));
	}
	
	//GET-POST-BY-USER_ID
	@GetMapping("/user/{userId}/posts")
	@Operation(summary = "Get Post By UserId")
	public ResponseEntity<List<PostDto>> getPostByUserId(@PathVariable Integer userId){
		return ResponseEntity.ok(this.postService.getPostByUserId(userId));
	}
	
	//GET-SINGLE-POST
	@GetMapping("/posts/{postId}")
	@Operation(summary = "Get Post By PostId")
	public ResponseEntity<PostDto> getpostById(@PathVariable Integer postId){
		return ResponseEntity.ok(this.postService.getPostById(postId));
	}
	
	//GET-ALL-POSTS
	@GetMapping("/posts")
	@Operation(summary = "Get All")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
			){
			return ResponseEntity.ok(this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir));
	}
	
	//DELETE-POSTS
	@DeleteMapping("/posts/{postId}")
	@Operation(summary = "Delete Posts")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Has Been Deleted Successfully !!",true),HttpStatus.OK);
	}
	
	//UPDATE-POSTS
	@PutMapping("/posts/{postId}")
	@Operation(summary = "Update Posts")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
	return ResponseEntity.ok(this.postService.upadtePost(postDto, postId));
	}
	
	
	//SEARCH-POSTS-BY-TITLE
	@GetMapping("/posts/search/{keywords}")
	@Operation(summary = "Search Post By Title")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords){
		return ResponseEntity.ok(this.postService.searchPost(keywords));
	}
	
	//UPLOAD-IMAGES-MEANS-UPDATE-THE-IMAGE-TO-THE-POST-ID
	@PostMapping("/posts/image/upload/{postId}")
	@Operation(summary = "Upload Image For Post/ Update ImageName To PostId")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam MultipartFile image,
			@PathVariable Integer postId) throws IOException{
		
		PostDto postById = this.postService.getPostById(postId);
		
		String uploadImage = this.fileService.uploadImage(path, image);
		
		postById.setImageName(uploadImage);
		PostDto upadtePostImage = this.postService.upadtePost(postById, postId);
		return new ResponseEntity<PostDto>(upadtePostImage,HttpStatus.OK);
	}
	
	//SERVE-UPLOADED-IMAGE-MEANS-SEE-THE-UPLOADED-IMAGE-DOWNLOAD
//	@Operation(summary = "Serve Post Uploaded Image")
//	@GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
//	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
//		InputStream resource = this.fileService.getResource(path, imageName);
//		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//		StreamUtils.copy(resource, response.getOutputStream());
//	}
	
	  @GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	    public ResponseEntity<byte[]> downloadImage(@PathVariable String imageName) {
	        try {
	            InputStream resource = fileService.getResource(path,imageName);
	            byte[] imageBytes = IOUtils.toByteArray(resource);

	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.IMAGE_JPEG);

	            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
	        } catch (IOException e) {
	            // Handle exception appropriately, e.g., log it and return an error response
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
}
