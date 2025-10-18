package com.giri.smart_inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giri.smart_inventory.dto.PageResponse;
import com.giri.smart_inventory.dto.ProductResponse;
import com.giri.smart_inventory.exception.ProductNotFoundException;
import com.giri.smart_inventory.model.Product;
import com.giri.smart_inventory.service.ProductService;



@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@PostMapping("/addProduct")
	public Product addProduct(@RequestBody Product p) {
		return productService.addProduct(p);
	}
	
	@GetMapping("/getAllProducts")
	public List<Product> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		return products;
	}
	
	@PostMapping("/addMultipleProducts")
	public List<Product> addMultipleProducts(@RequestBody List<Product> products) {
		return productService.addMultipleProducts(products);
	}
	
	
	@GetMapping("/getProductById/{id}")
	public ResponseEntity<?> getProductById(@PathVariable int id) {
		try {
			Product product = productService.getProductById(id);
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		} catch (ProductNotFoundException ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);

		}

	}
	
	@DeleteMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Product deleted successfully!";
    }
	
	@GetMapping("/getProductByPagination/{pageNumber}/{pageSize}")
	public Page<Product> getProductByPagination(@PathVariable int pageNumber, @PathVariable int pageSize) {
		Page<Product> products = productService.getProductByPagination(pageNumber, pageSize);
		return products;
	}

}
