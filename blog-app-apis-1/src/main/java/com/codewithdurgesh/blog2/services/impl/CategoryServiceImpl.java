package com.codewithdurgesh.blog2.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog2.entities.Category;
import com.codewithdurgesh.blog2.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog2.payloads.CategoryDto;
import com.codewithdurgesh.blog2.repositories.CategoryRepo;
import com.codewithdurgesh.blog2.services.CategoryService;



@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category catmap = this.modelMapper.map(categoryDto, Category.class);
		Category mapcat = this.categoryRepo.save(catmap);
		return this.modelMapper.map(mapcat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedcategory = this.categoryRepo.save(category);
		return this.modelMapper.map(updatedcategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category deletcat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		this.categoryRepo.delete(deletcat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category getsinglecat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		return this.modelMapper.map(getsinglecat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> findAllcat = this.categoryRepo.findAll();
		List<CategoryDto> collectAllCat = findAllcat.stream().map((cat)-> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return collectAllCat;
	}

}
