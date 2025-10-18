package com.giri.smart_inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.giri.smart_inventory.exception.ProductNotFoundException;
import com.giri.smart_inventory.model.Category;
import com.giri.smart_inventory.model.Product;
import com.giri.smart_inventory.repository.CategoryRepository;
import com.giri.smart_inventory.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;


	public Product addProduct(Product product) {
        attachCategory(product);
        return productRepository.save(product);
    }

	public List<Product> getAllProducts() {
		
		return productRepository.findAll();
	}

	public Product getProductById(long id) {
		Optional<Product> productBox = productRepository.findById(id);
		Product product = null;
		if (productBox.isPresent()) {
			product = productBox.get();

		} else
			throw new ProductNotFoundException("Product Not Found");
		return product;
	}

	public List<Product> addMultipleProducts(List<Product> products) {
        for (Product product : products) {
            attachCategory(product);
        }
        return productRepository.saveAll(products);
    }

	public void deleteProduct(Long id) {
		 productRepository.deleteById(id);
		
	}
	
	public Page<Product> getProductByPagination(int pageNumber, int pageSize) {

		return productRepository.findAll(PageRequest.of(pageNumber, pageSize));
	}
	
	private void attachCategory(Product product) {
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Long categoryId = product.getCategory().getId();
            Category existingCategory = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
            product.setCategory(existingCategory);
        } else {
            throw new RuntimeException("Category ID must be provided for product: " + product.getProductTitle());
        }
    }
}
