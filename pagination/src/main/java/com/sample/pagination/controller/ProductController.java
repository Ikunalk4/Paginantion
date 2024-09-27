package com.sample.pagination.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sample.pagination.dto.ProductDto;
import com.sample.pagination.entity.Product;
import com.sample.pagination.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping()
	public ResponseEntity<List<Product>> getProducts(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize) {

		Page<Product> products = productService.getProducts(pageNo, pageSize);
		return new ResponseEntity<>(products.getContent(), HttpStatus.OK); 
	}

	@PostMapping()
	public ResponseEntity<Product> createProducts(@RequestBody ProductDto productDto) {
		return new ResponseEntity<>(productService.createProduct(productDto), HttpStatus.CREATED);
	}

	@GetMapping("/sort")
	public ResponseEntity<List<Product>> sortProductsById(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String sortOrder) {

		Sort sort = Sort.by(sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

		Page<Product> products = productService.sortProducts(pageable);
		return new ResponseEntity<>(products.getContent(), HttpStatus.OK);
	}
	
	@GetMapping("/type/{productType}")
	public ResponseEntity<List<Product>> getProductsByProductType(
	        @PathVariable String productType,
	        @RequestParam(defaultValue = "0") int pageNo,
	        @RequestParam(defaultValue = "10") int pageSize,
	        @RequestParam(defaultValue = "id") String sortBy,
	        @RequestParam(defaultValue = "asc") String sortOrder) {

	    Page<Product> products = productService.getProductByProductType(productType, pageNo, pageSize, sortBy, sortOrder);
	    return new ResponseEntity<>(products.getContent(), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@RequestBody ProductDto productDto, @PathVariable("id") Integer id) throws Exception{
		return new ResponseEntity<>(productService.updateProduct(id,productDto),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("id") int id) {
	    if (productService.existsById(id) ) {
	        productService.deleteProductById(id);
	        return ResponseEntity.noContent().build(); // 204 No Content
	    } else {
	        return ResponseEntity.notFound().build(); // 404 Not Found
	    }
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
		return new ResponseEntity<>(productService.getProductById(id),HttpStatus.OK);
	}


}
