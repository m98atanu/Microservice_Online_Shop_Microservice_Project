package com.microservices.shoppingservice.externalAPI.productAPI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductServiceEndpoints {
	@PostMapping("/api/product/add")
	public ResponseEntity<Integer> addProduct(@RequestBody ProductEntity product);
	
	@GetMapping("/api/product/{productId}")
	public ResponseEntity<ProductEntity> fetchProductById(@PathVariable int productId);
	
//	@PutMapping("/api/product/update/{productId}")
//	public ResponseEntity<String> updateProduct(@PathVariable int productId, @RequestBody UpdatedProduct updatedProduct);

	@DeleteMapping("/api/product/delete/{productId}")
	public ResponseEntity<String> deleteProductById(@PathVariable int productId);
}
