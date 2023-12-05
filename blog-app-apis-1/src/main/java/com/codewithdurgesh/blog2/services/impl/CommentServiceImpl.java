package com.codewithdurgesh.blog2.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog2.entities.Category;
import com.codewithdurgesh.blog2.entities.Comment;
import com.codewithdurgesh.blog2.entities.Post;
import com.codewithdurgesh.blog2.entities.User;
import com.codewithdurgesh.blog2.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog2.payloads.CommentDto;
import com.codewithdurgesh.blog2.payloads.CommentResponse;
import com.codewithdurgesh.blog2.repositories.CategoryRepo;
import com.codewithdurgesh.blog2.repositories.CommentRepo;
import com.codewithdurgesh.blog2.repositories.PostRepo;
import com.codewithdurgesh.blog2.repositories.UserRepo;
import com.codewithdurgesh.blog2.services.CommentService;


@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PostRepo postRepo;
	
 	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id ", postId));

		Comment comment = this.modelMapper.map(commentDto, Comment.class);

		comment.setPost(post);

		Comment savedComment = this.commentRepo.save(comment);

		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {

		Comment com = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));
		this.commentRepo.delete(com);
	}

}
