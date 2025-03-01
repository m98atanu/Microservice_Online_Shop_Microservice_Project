package com.microservices.inventory.external.APIEndpoints;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductServiceEndpoints {
	
	@GetMapping("/api/product/{productId}")
	public ResponseEntity<ProductEntity> fetchProductById(@PathVariable int productId);
	
}
