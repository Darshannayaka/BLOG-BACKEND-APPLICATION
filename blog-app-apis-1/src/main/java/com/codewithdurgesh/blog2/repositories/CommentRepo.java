package com.codewithdurgesh.blog2.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithdurgesh.blog2.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

	
}
