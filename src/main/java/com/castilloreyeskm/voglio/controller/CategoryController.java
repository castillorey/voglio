package com.castilloreyeskm.voglio.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.castilloreyeskm.voglio.exceptions.AlreadyExistException;
import com.castilloreyeskm.voglio.exceptions.ResourceNotFoundException;
import com.castilloreyeskm.voglio.model.Category;
import com.castilloreyeskm.voglio.response.ApiResponse;
import com.castilloreyeskm.voglio.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
	private final ICategoryService categoryService;

	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllCategories() {
		try {
			List<Category> categories = categoryService.getAllCategories();
			return ResponseEntity.ok(new ApiResponse("Categories found", categories));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:", INTERNAL_SERVER_ERROR));
		}
	}
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addCategory(@RequestBody Category request) {
		try {
			Category newCategory = categoryService.addCategory(request);
			return ResponseEntity.ok(new ApiResponse("Add successful", newCategory));
		} catch (AlreadyExistException e) {
			return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/{id}/category")
	public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
		try {
			Category category = categoryService.getCategoryById(id);
			return ResponseEntity.ok(new ApiResponse("Category found", category));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@DeleteMapping("/{id}/delete")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
		try {
			categoryService.deleteCategoryById(id);
			return ResponseEntity.ok(new ApiResponse("Delete successful", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody Category category) {
        try {
            Category updatedCategory = categoryService.updateCategory(category);
            return ResponseEntity.ok(new ApiResponse("Update successful", updatedCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
