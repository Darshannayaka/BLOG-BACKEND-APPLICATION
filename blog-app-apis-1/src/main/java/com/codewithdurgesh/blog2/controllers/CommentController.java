package com.codewithdurgesh.blog2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog2.config.AppConstants;
import com.codewithdurgesh.blog2.payloads.ApiResponse;
import com.codewithdurgesh.blog2.payloads.CommentDto;
import com.codewithdurgesh.blog2.payloads.CommentResponse;
import com.codewithdurgesh.blog2.services.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@SecurityRequirement(name = "scheme1")
@RequestMapping("/api/v1/")
@Tag(name = "CommentController", description = "REST API's Related To Perform Comment Operation !! ")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	
	@PostMapping("/post/{postId}/comments")
	@Operation(summary = "Create Comment")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable Integer postId) {

		CommentDto createComment = this.commentService.createComment(comment, postId);
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
	}

	@DeleteMapping("/comments/{commentId}")
	@Operation(summary = "Delete Comments")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {

		this.commentService.deleteComment(commentId);

		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully !!", true), HttpStatus.OK);
	}
}
