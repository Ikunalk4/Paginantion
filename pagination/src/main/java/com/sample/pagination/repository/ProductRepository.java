package com.sample.pagination.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sample.pagination.entity.Product;

public interface ProductRepository extends MongoRepository<Product, Integer>{
	
	@Query("{ 'productType': { $regex: ?0, $options: 'i' } }")
	Page<Product> findByProductType(String productType, Pageable pageable); 
}
