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
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog2.payloads.ApiResponse;
import com.codewithdurgesh.blog2.payloads.CategoryDto;
import com.codewithdurgesh.blog2.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@SecurityRequirement(name = "scheme1")
@RequestMapping("/api/v1/categories")
@Tag(name = "CategoryController", description = "REST API's Related To Perform Catgeory Operation !! ")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	//CREATE
	@PostMapping("/")
	@Operation(summary = "Create Catgeory")
	public ResponseEntity<CategoryDto> createcategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createdCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createdCategory,HttpStatus.CREATED);
	}
	
	//UPDATE
	@PutMapping("/{catId}")
	@Operation(summary = "Update Catgeory")
	public ResponseEntity<CategoryDto> updatecategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer catId){
		CategoryDto createdCategory = this.categoryService.updateCategory(categoryDto, catId);
		return ResponseEntity.ok(createdCategory);
	}
	
	//DELETE
	@DeleteMapping("/{catId}")
	@Operation(summary = "Delete Catgeory")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId){
		this.categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully",true),HttpStatus.OK);
	}
	
	//GET-BY-ID
	@GetMapping("/{catId}")
	@Operation(summary = "Get Single Catgeory")
	public ResponseEntity<CategoryDto> getbyCategoryId(@PathVariable Integer catId){
		return  ResponseEntity.ok(this.categoryService.getCategory(catId));
	}
	
	
	//GET-ALL
	@GetMapping("/")
	@Operation(summary = "Get All Catgeory")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
	return ResponseEntity.ok(this.categoryService.getCategories());
	}
}
