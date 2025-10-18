package com.giri.smart_inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giri.smart_inventory.dto.ProductResponse;
import com.giri.smart_inventory.exception.CategoryNotFoundException;
import com.giri.smart_inventory.model.Category;
import com.giri.smart_inventory.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@PostMapping("/addCategory")
	public Category addCategory(@RequestBody Category c) {
		return categoryService.addCategory(c);
	}
	
	@PostMapping("/addMultipleCategories")
	public List<Category> addMultipleCategories(@RequestBody List<Category> categories) {
		return categoryService.addMultipleCategories(categories);
	}
	
	@GetMapping("/getAllCategories")
	public List<Category> getAllCategories() {
		List<Category> categories = categoryService.getAllCategories();
		return categories;
	}
	
	@GetMapping("/{categoryId}/products")
	public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable Long categoryId) {
	    List<ProductResponse> products = categoryService.getProductsByCategory(categoryId);
	    return ResponseEntity.ok(products);
	}
	
	
	
	@GetMapping("/getCategoryById/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable long id) {
		try {
			Category category = categoryService.getCategoryById(id);
			return new ResponseEntity<Category>(category, HttpStatus.OK);
		} catch (CategoryNotFoundException ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);

		}

	}

}
