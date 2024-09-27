package com.sample.pagination.service;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.pagination.dto.ProductDto;
import com.sample.pagination.entity.Product;
import com.sample.pagination.exception.ProductsNotFoundExceptionSteps;
import com.sample.pagination.repository.ProductRepository;

@Service
public class ProductService {
	
	private ProductRepository productRepository;
	private final ObjectMapper objectMapper;
	
	public ProductService(ProductRepository productRepository, ObjectMapper objectMapper) {
		this.productRepository = productRepository;
		this.objectMapper = objectMapper;
	}

	public Page<Product> getProducts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productRepository.findAll(pageable);
    }

	public Product createProduct(ProductDto productDto) {
		Product product = objectMapper.convertValue(productDto, Product.class);
		return productRepository.save(product);
	}
	
	public Page<Product> sortProducts(Pageable pageable) {
	    return productRepository.findAll(pageable);
	}
	
	public Page<Product> getProductByProductType(String productType, int pageNo, int pageSize, String sortBy, String sortOrder) {
		
		Sort sort = Sort.by(sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		return productRepository.findByProductType(productType, pageable);
	}

	public Product updateProduct(Integer id, ProductDto productDto) throws Exception {
		Product product = objectMapper.convertValue(productDto, Product.class);
		Optional<Product> productExists = productRepository.findById(id);
		if(productExists.isPresent()) {
			return productRepository.save(product);
		}else {
			throw new ProductsNotFoundExceptionSteps("Record Notfound");
		}
	}

	public void deleteProductById(int id) {
		productRepository.deleteById(id);
	}

	public Product getProductById(int id) {
	    Optional<Product> optionalProduct = productRepository.findById(id);
	    if (optionalProduct.isPresent()) {
	        return optionalProduct.get();
	    } else {
	        throw new ProductsNotFoundExceptionSteps("Product not found with ID: " + id);
	    }
	}

	public boolean existsById(int id) {
		return productRepository.existsById(id);
	}



}
