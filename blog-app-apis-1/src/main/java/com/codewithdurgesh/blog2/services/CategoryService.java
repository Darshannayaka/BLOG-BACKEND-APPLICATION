package com.codewithdurgesh.blog2.services;

import java.util.List;

import com.codewithdurgesh.blog2.payloads.CategoryDto;


public interface CategoryService {

	//CREATE
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//UPDATE
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	//DELETE
	void deleteCategory(Integer categoryId);
	
	//GET-BY-ID
	CategoryDto getCategory(Integer categoryId);
	
	//GET-ALL
	List<CategoryDto> getCategories();
}
