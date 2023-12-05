package com.codewithdurgesh.blog2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithdurgesh.blog2.entities.Category;


public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
