package com.giri.smart_inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giri.smart_inventory.dto.ProductResponse;
import com.giri.smart_inventory.exception.CategoryNotFoundException;
import com.giri.smart_inventory.model.Category;
import com.giri.smart_inventory.repository.CategoryRepository;


@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;

	public Category addCategory(Category c) {
		
		return categoryRepository.save(c);
	}

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	public Category getCategoryById(long id) {
		Optional<Category> categoryBox = categoryRepository.findById(id);
		Category category = null;
		if (categoryBox.isPresent()) {
			category = categoryBox.get();

		} else
			throw new CategoryNotFoundException("Category Not Found");
		return category;
	}

	public List<Category> addMultipleCategories(List<Category> categories) {
		
		return categoryRepository.saveAll(categories);
	}
	
	public List<ProductResponse> getProductsByCategory(Long categoryId) {
	    Category category = getCategoryById(categoryId);

	    return category.getProducts()
	            .stream()
	            .map(p -> new ProductResponse(p.getId(), p.getProductTitle(), p.getProductDesc()))
	            .toList();
	}
}
