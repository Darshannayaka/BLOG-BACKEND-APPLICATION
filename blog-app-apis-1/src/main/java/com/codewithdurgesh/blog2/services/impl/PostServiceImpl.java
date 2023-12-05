package com.codewithdurgesh.blog2.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog2.entities.Category;
import com.codewithdurgesh.blog2.entities.Post;
import com.codewithdurgesh.blog2.entities.User;
import com.codewithdurgesh.blog2.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog2.payloads.PostDto;
import com.codewithdurgesh.blog2.payloads.PostResponse;
import com.codewithdurgesh.blog2.repositories.CategoryRepo;
import com.codewithdurgesh.blog2.repositories.PostRepo;
import com.codewithdurgesh.blog2.repositories.UserRepo;
import com.codewithdurgesh.blog2.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		Category catId = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Catgeory", "Catgeory Id", categoryId));
		
		User usrId = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
		
		Post createpost = this.modelMapper.map(postDto, Post.class);
		createpost.setImageName("default.png");
		createpost.setAddedDate(new Date());
		createpost.setCategory(catId);
		createpost.setUser(usrId);
		
		Post savePost = this.postRepo.save(createpost);
		return this.modelMapper.map(savePost, PostDto.class);
	}

	@Override
	public PostDto upadtePost(PostDto postDto, Integer postId) {
		Post findpost = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		
		Category category = this.categoryRepo.findById(postDto.getCategory().getCategoryId()).get();
		
		findpost.setTitle(postDto.getTitle());
		findpost.setContent(postDto.getContent());
		findpost.setImageName(postDto.getImageName());
		findpost.setCategory(category);
		Post upatedPost = this.postRepo.save(findpost);
		return this.modelMapper.map(upatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post postDel = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		this.postRepo.delete(postDel);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		//TERNARY-OPERATOR-IF-AND-ELSE
		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> allPosts = pagePost.getContent();
		List<PostDto> postDtos = allPosts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post postbyId = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		return this.modelMapper.map(postbyId, PostDto.class);
	}

	@Override
	public List<PostDto> getPostBycategoryId(Integer categoryId) {
		Category categories = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		List<Post> findByCategories = this.postRepo.findByCategory(categories);
		List<PostDto> collectcategory = findByCategories.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return collectcategory;
	}

	@Override
	public List<PostDto> getPostByUserId(Integer userId) {
		User userspost = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "user id", userId));
		List<Post> findByUsers = this.postRepo.findByUser(userspost);
		List<PostDto> collectusers = findByUsers.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return collectusers;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		//List<Post> searchposts = this.postRepo.searchByTitle("%"+keyword+"%");
		List<Post> searchposts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> searchedPosts = searchposts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return searchedPosts;
	}

}
