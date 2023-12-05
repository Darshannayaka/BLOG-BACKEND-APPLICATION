package com.codewithdurgesh.blog2.services;

import java.util.List;

import com.codewithdurgesh.blog2.payloads.PostDto;
import com.codewithdurgesh.blog2.payloads.PostResponse;

public interface PostService {

	//CREATE
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//UPDATE
	PostDto upadtePost(PostDto postDto, Integer postId);
	
	//DELETE
	void deletePost(Integer postId);
	
	//GET-ALL-POSTS
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//GET-POST-BY-ID
	PostDto getPostById(Integer postId);
	
	//GET-POST-BY-CATEGORY_ID
	List<PostDto> getPostBycategoryId(Integer categoryId);
	
	//GET-POST-BY-USER_ID
	List<PostDto> getPostByUserId(Integer userId);
	
	//SEARCH-POSTS
	List<PostDto> searchPost(String keyword);
}
