package com.codewithdurgesh.blog2.payloads;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private Integer categoryId;
	
	@NotBlank
	@Size(min = 4, message = "Title Should be minimum 4 characters")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10, max = 15,message = "Description should be minimum 10 maximum 15 characters")
	private String categoryDescription;
}
