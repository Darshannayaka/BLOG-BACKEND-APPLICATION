package com.codewithdurgesh.blog2.services;


import com.codewithdurgesh.blog2.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Integer postId);

	void deleteComment(Integer commentId);

}
