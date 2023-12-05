package com.codewithdurgesh.blog2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithdurgesh.blog2.entities.Category;
import com.codewithdurgesh.blog2.entities.Post;
import com.codewithdurgesh.blog2.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	List<Post> findByCategory(Category category);
	
	List<Post> findByUser(User user);
	
	//HIBERNATE 6.2.13 JAR PROBELM FIXED IN CREATING LIKE %key%
	List<Post> findByTitleContaining(String title);
	
	//IF ABOVE CODE DOES NOT WORK USE BELOW CODE PROBLEM IN HIBERNATE 5.6.6 and 5.6.7 JAR
	
	//@Query("select p from Post p where p.title like :key")
	//List<Post> searchByTitle(@Param("key") String title);
	
}
